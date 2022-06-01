package com.githunt

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.request.SuccessResult
import com.github.ui.owner.details.OwnerDetailsViewModel
import com.github.ui.search.SearchViewModel
import com.githunt.engine.githubApi
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainActivity : ComponentActivity() {
    private val api = githubApi
    private val dispatch = Dispatchers.IO
    private val searchViewModel: SearchViewModel = SearchViewModel(api, dispatch)
    private val ownerDetailsViewModel: OwnerDetailsViewModel =
        OwnerDetailsViewModel(api, dispatch) { getPalette(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppContent(searchViewModel, ownerDetailsViewModel)
        }
    }
}

suspend fun getPalette(result: SuccessResult): Color = suspendCoroutine { continuation ->
    Palette.from(result.drawable.toBitmap(config = Bitmap.Config.RGBA_F16))
        .maximumColorCount(16)
        .generate {
            continuation.resume(
                Color(
                    it?.lightMutedSwatch?.rgb
                        ?: it?.mutedSwatch?.rgb
                        ?: ColorUtils.setAlphaComponent(it?.swatches?.firstOrNull()?.rgb ?: 0, 204)

                )
            )
        }
}