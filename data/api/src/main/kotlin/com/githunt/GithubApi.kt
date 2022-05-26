package com.githunt

import com.githunt.models.GithubOwnersWrapper
import com.githunt.models.GithubRepositoryWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    companion object {
        const val URL = "https://api.github.com/"
    }

    @GET("search/users?&sort=followers&per_page=25")
    suspend fun searchUsers(
        @Query(value = "q", encoded = true) user: String
    ): GithubOwnersWrapper

    @GET("search/repositories?&sort=stars&order=desc&per_page=3")
    suspend fun getOrganizationsRepos(
        @Query(value = "q", encoded = true) organization: String
    ): GithubRepositoryWrapper
}