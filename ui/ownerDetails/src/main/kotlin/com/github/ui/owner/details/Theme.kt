package com.github.ui.owner.details

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class OwnerDetailColors(
    val gold: Color = Gold,
    val silver: Color = Silver,
    val bronze: Color = Bronze
)

val OwnerDetailsCustomColors = staticCompositionLocalOf {
    OwnerDetailColors(
        gold = Gold,
        silver = Silver,
        bronze = Bronze
    )
}

@Composable
fun OwnerDetailsTheme(
    /* ... */
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(OwnerDetailsCustomColors provides OwnerDetailColors()) {
        MaterialTheme(
            /* colors = ..., typography = ..., shapes = ... */
            content = content
        )
    }
}

object OwnerDetailsTheme {
    val colors: OwnerDetailColors
        @Composable
        get() = OwnerDetailsCustomColors.current
}