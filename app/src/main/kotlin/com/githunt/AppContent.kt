package com.githunt

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.ui.owner.details.OwnerDetailsViewModel
import com.github.ui.search.SearchViewModel
import com.githunt.ui.theme.GithuntTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun AppContent(
    searchViewModel: SearchViewModel,
    ownerDetailsViewModel: OwnerDetailsViewModel
) {
    val navController = rememberAnimatedNavController()

    Scaffold { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            GithuntTheme(true) {
                AppNavigation(navController, searchViewModel, ownerDetailsViewModel)
            }
        }
    }
}