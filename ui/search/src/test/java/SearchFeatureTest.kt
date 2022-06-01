import app.cash.turbine.test
import com.github.ui.search.SearchViewModel
import com.githunt.GithubApi
import com.githunt.models.GithubOwner
import com.githunt.models.GithubOwnersWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SearchFeatureTest {

    private val githubApi: GithubApi = mockk()
    private val items = listOf(GithubOwner("nytimes"), GithubOwner("square"))
    private val items2 = listOf(GithubOwner("touchlab"), GithubOwner("jetbrains"))
    private val testCoroutineScheduler = TestCoroutineScheduler()
    private val testCoroutineDispatcher = StandardTestDispatcher(testCoroutineScheduler)
    private val viewModel: SearchViewModel = SearchViewModel(githubApi, testCoroutineDispatcher)


    @Before
    fun before() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun searchUsersSuccess() = runTest {
        launch {
            viewModel.usersFlow.test {
                assertEquals(items, awaitItem())
                assertEquals(items2, awaitItem())
            }
        }

        coEvery { githubApi.searchUsers(any()) } returns Response.success(
            GithubOwnersWrapper(items)
        )
        viewModel.updateSearchQuery("a")
        advanceTimeBy(600)

        coEvery { githubApi.searchUsers(any()) } returns Response.success(
            GithubOwnersWrapper(items2)
        )
        viewModel.updateSearchQuery("ab")
        advanceTimeBy(600)
    }

    @Test
    fun searchUsersFailure() = runTest {
        coEvery { githubApi.searchUsers(any()) } returns Response.error(
            403,
            "Error".toResponseBody()
        )
        viewModel.updateSearchQuery("nytimes")
        launch {
            viewModel.usersFlow.test {
                expectNoEvents()
            }
        }
    }
}