package com.github.ui.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githunt.engine.githubApi
import com.githunt.models.GithubOwner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class SearchViewModel : ViewModel() {
    private val searchQueryChannel = Channel<String>(Channel.CONFLATED)
//    var userQueryResults = mutableStateOf(listOf<GithubOwner>())

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val usersFlow = searchQueryChannel
        .receiveAsFlow()
        .filterNot { it.isBlank() }
        .debounce(QUERY_INPUT_DELAY_MILLIS)
        .flatMapLatest { flowOf(githubApi.searchUsers(formatQueryParam(it)).items)}

    private fun formatQueryParam(it: String) = "$it+type:org"

    fun updateSearchQuery(query: String) {
        searchQueryChannel.trySend(query)
    }

    companion object {
        private const val QUERY_INPUT_DELAY_MILLIS = 300L
    }
}