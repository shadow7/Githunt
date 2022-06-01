package com.github.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import com.githunt.GithubApi
import com.githunt.models.GithubOwner
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class SearchViewModel(private val githubApi: GithubApi, dispatcher: CoroutineDispatcher) :
    ViewModel() {
    private val searchQueryChannel = Channel<String>(Channel.CONFLATED)

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val usersFlow: Flow<List<GithubOwner>> = searchQueryChannel
        .receiveAsFlow()
        .filterNot { it.isBlank() }
        .debounce(QUERY_INPUT_DELAY_MILLIS)
        .flatMapLatest { githubOwners(it) }
        .filterNot { it.isEmpty() }
        .flowOn(dispatcher)

    private suspend fun githubOwners(query: String): Flow<List<GithubOwner>> = flowOf(
        try {
            val response = githubApi.searchUsers(formatQueryParam(query))
            val body = response.body()
            if (response.isSuccessful && body != null) {
                body.items
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Log.e(SearchViewModel::class.simpleName, "GithubOwners call failed", e)
            emptyList()
        }
    )

    private fun formatQueryParam(it: String) = "$it+type:org"

    fun updateSearchQuery(query: String) {
        searchQueryChannel.trySend(query)
    }

    companion object {
        private const val QUERY_INPUT_DELAY_MILLIS = 400L
    }
}