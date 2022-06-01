import androidx.compose.ui.graphics.Color
import app.cash.turbine.test
import coil.request.SuccessResult
import com.github.ui.owner.details.OwnerDetailsViewModel
import com.githunt.GithubApi
import com.githunt.models.GithubOwnerProject
import com.githunt.models.GithubRepositoryWrapper
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
class OwnerDetailsTest {

    private val githubApi: GithubApi = mockk()
    private val successResult: SuccessResult = mockk()
    private val items = listOf(GithubOwnerProject("okhttp"), GithubOwnerProject("okio"))
    private val dispatcher = StandardTestDispatcher(TestCoroutineScheduler())
    private val paletteColor = Color.DarkGray
    private val viewModel: OwnerDetailsViewModel =
        OwnerDetailsViewModel(githubApi, dispatcher) { paletteColor }

    @Before
    fun before() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun searchSuccess() = runTest {
        launch {
            viewModel.projectsFlow.test {
                assertEquals(items, awaitItem())
                assertEquals(items, awaitItem())
                assertEquals(items, awaitItem())
            }
        }

        coEvery { githubApi.searchOrgsTopRepos(any()) } returns Response.success(
            GithubRepositoryWrapper(items)
        )
        viewModel.updateSearchQuery("a")
        advanceTimeBy(100)
        viewModel.updateSearchQuery("b")
        advanceTimeBy(100)
        viewModel.updateSearchQuery("c")
        advanceTimeBy(100)
    }

    @Test
    fun searchFailure() = runTest {
        launch {
            viewModel.projectsFlow.test {
                expectNoEvents()
            }
        }

        coEvery { githubApi.searchOrgsTopRepos(any()) } returns Response.error(
            403,
            "Error".toResponseBody()
        )
        viewModel.updateSearchQuery("nytimes")
    }

    @Test
    fun getSuccessPalette() = runTest {
        launch {
            viewModel.imageBackgroundFlow.test {
                assertEquals(paletteColor, awaitItem())
            }
        }
        viewModel.updateAvatarDownloadResults(successResult)
    }

}