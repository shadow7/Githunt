package com.githunt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.github.ui.owner.details.OwnerDetailsViewModel
import com.github.ui.search.SearchViewModel
import com.githunt.common.getPalette
import com.githunt.engine.githubApi
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {
    //TODO: Inject these values with Dagger+Hilt
    private val api = githubApi
    private val dispatch = Dispatchers.IO
    private val searchViewModel: SearchViewModel = SearchViewModel(api, dispatch)
    private val ownerDetailsViewModel: OwnerDetailsViewModel =
        OwnerDetailsViewModel(api, dispatch) { it.getPalette() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppContent(searchViewModel, ownerDetailsViewModel)
        }
    }
}