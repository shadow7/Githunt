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
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
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
    fun searchSuccess() = runBlocking {
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
        viewModel.updateSearchQuery("b")
        viewModel.updateSearchQuery("c")
    }

    @Test
    fun searchFailure() = runBlocking {
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
    fun getSuccessPalette() = runBlocking {
        launch {
            viewModel.imageBackgroundFlow.test {
                assertEquals(paletteColor, awaitItem())
            }
        }
        viewModel.updateAvatarDownloadResults(successResult)
    }

}