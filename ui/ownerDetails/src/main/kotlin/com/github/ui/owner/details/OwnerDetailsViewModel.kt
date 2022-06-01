package com.github.ui.owner.details

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import coil.request.SuccessResult
import com.githunt.GithubApi
import com.githunt.models.GithubOwnerProject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

class OwnerDetailsViewModel(
    githubApi: GithubApi,
    dispatcher: CoroutineDispatcher,
    getPalette: suspend (SuccessResult) -> Color
) : ViewModel() {

    private val avatarDownloadResults: Channel<SuccessResult> = Channel(Channel.CONFLATED)
    private val searchQuery: Channel<String> = Channel(Channel.CONFLATED)

    val imageBackgroundFlow: Flow<Color> = avatarDownloadResults.receiveAsFlow()
        .flatMapLatest {
            callbackFlow {
                trySend(getPalette(it))
                awaitClose { cancel() }
            }
        }

    val projectsFlow: Flow<List<GithubOwnerProject>> = searchQuery.receiveAsFlow()
        .flatMapLatest { query ->
            flowOf(
                try {
                    val result = githubApi.searchOrgsTopRepos("org:$query")
                    val body = result.body()
                    if (result.isSuccessful && body != null) {
                        body.items
                            .sortedWith(compareByDescending { item -> item.stars })
                    } else {
                        emptyList()
                    }
                } catch (e: Exception) {
                    Log.e(
                        OwnerDetailsViewModel::class.simpleName,
                        "organizationRepos call failed",
                        e
                    )
                    emptyList()
                }
            )
        }.filterNot { it.isEmpty() }
        .flowOn(dispatcher)

    fun updateAvatarDownloadResults(result: SuccessResult) {
        avatarDownloadResults.trySend(result)
    }

    fun updateSearchQuery(query: String) {
        searchQuery.trySend(query)
    }
}
