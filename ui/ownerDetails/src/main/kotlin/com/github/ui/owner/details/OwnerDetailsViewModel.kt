package com.github.ui.owner.details

import androidx.lifecycle.ViewModel
import com.githunt.engine.githubApi
import com.githunt.models.GithubOwnerProject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class OwnerDetailsViewModel : ViewModel() {
    private val searchQueryFlow: MutableStateFlow<String> = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val organizationProjectsList: Flow<List<GithubOwnerProject>> = searchQueryFlow
        .filterNot { it.isBlank() }
        .flatMapLatest { flowOf(githubApi.getOrganizationsRepos(it).items) }

    fun updateSearchQuery(query: String) {
        searchQueryFlow.value = query
    }
}
