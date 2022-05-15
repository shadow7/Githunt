import models.GithubSearchWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    companion object {
        const val URL = "https://api.github.com/"
    }

    @GET("search/repositories?&sort=stars&order=desc&per_page=3")
    suspend fun getTopStarredRepos(
        @Query("q") organization: String
    ): GithubSearchWrapper
}