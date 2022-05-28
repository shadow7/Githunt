package com.github.ui.owner.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githunt.engine.githubApi
import com.githunt.models.GithubOwnerProject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext

class OwnerDetailsViewModel : ViewModel() {
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun organizationRepos(query: String): List<GithubOwnerProject> =
        withContext(viewModelScope.coroutineContext) {
            githubApi.searchOrgsTopRepos("org:$query").items
                .sortedWith(compareByDescending { item -> item.stars })
        }
}
