package com.githunt.common

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.request.SuccessResult
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun SuccessResult.getPalette(): Color = suspendCoroutine { continuation ->
    Palette.from(drawable.toBitmap(config = Bitmap.Config.RGBA_F16))
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