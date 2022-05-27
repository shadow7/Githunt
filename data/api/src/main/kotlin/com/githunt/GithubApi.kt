package com.githunt

import com.githunt.models.GithubOwnersWrapper
import com.githunt.models.GithubRepositoryWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    companion object {
        const val URL = "https://api.github.com/"
    }

    @GET("search/users")
    suspend fun searchUsers(
        @Query(value = "q", encoded = true) user: String,
        @Query(value = "per_page") amount: Int = 25,
        @Query(value = "sort") sort: String = "followers"
    ): GithubOwnersWrapper

    @GET("search/repositories?&sort=stars&order=desc")
    suspend fun searchOrgsTopRepos(
        @Query(value = "q", encoded = true) org: String,
        @Query(value = "per_page") amount: Int = 10
    ): GithubRepositoryWrapper

}