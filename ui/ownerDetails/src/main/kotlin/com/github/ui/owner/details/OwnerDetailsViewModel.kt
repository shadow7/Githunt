package com.github.ui.owner.details

import androidx.lifecycle.ViewModel
import com.githunt.engine.githubApi
import com.githunt.models.GithubOwnerProject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class OwnerDetailsViewModel : ViewModel() {
    private val searchQueryFlow: MutableStateFlow<String> = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val organizationReposList: Flow<List<GithubOwnerProject>> = searchQueryFlow
        .filterNot { it.isBlank() }
        .flatMapLatest { query ->
            flowOf(githubApi.searchOrgsTopRepos("org:$query").items
                .sortedWith(compareByDescending { item -> item.stars })
            )
        }

    fun updateSearchQuery(query: String) {
        searchQueryFlow.value = query
    }
}
