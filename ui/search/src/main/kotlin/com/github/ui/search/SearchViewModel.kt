package com.github.ui.search

import androidx.lifecycle.ViewModel
import com.githunt.engine.githubApi
import com.githunt.models.GithubOwner
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class SearchViewModel : ViewModel() {
    val searchQueryFlow: MutableStateFlow<String> = MutableStateFlow(value = "")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val usersList: Flow<List<GithubOwner>> = searchQueryFlow
        .filter { it.isBlank() }
        .debounce(QUERY_INPUT_DELAY_MILLIS)
        .flatMapLatest { flowOf(githubApi.searchUsers(formatQueryParam(it)).items) }


    private fun formatQueryParam(it: String) = "$it+type:org"

    fun updateSearchQuery(query: String) {
        searchQueryFlow.value = query
    }

    companion object {
        private const val QUERY_INPUT_DELAY_MILLIS = 300L
    }
}