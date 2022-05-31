package com.github.ui.owner.details

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import coil.request.SuccessResult
import com.githunt.engine.githubApi
import com.githunt.models.GithubOwnerProject
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class OwnerDetailsViewModel : ViewModel() {

    private val successResults: Channel<SuccessResult> = Channel(Channel.CONFLATED)

    val imageBackgroundFlow: Flow<Color> = successResults.receiveAsFlow()
        .flatMapLatest {
            callbackFlow {
                Palette.from(it.drawable.toBitmap(config = Bitmap.Config.RGBA_F16))
                    .maximumColorCount(16)
                    .generate {
                        viewModelScope.launch {
                            trySend(
                                Color(
                                    it?.lightMutedSwatch?.rgb
                                        ?: it?.mutedSwatch?.rgb
                                        ?: Color.Blue.toArgb()
                                )
                            )
                        }
                    }
                awaitClose { cancel() }
            }
        }

    fun updateResults(result: SuccessResult) {
        successResults.trySend(result)
    }

    fun organizationRepos(query: String): Flow<List<GithubOwnerProject>> =
        flow {
            emit(
                githubApi.searchOrgsTopRepos("org:$query").items
                    .sortedWith(compareByDescending { item -> item.stars })
            )
        }
}
