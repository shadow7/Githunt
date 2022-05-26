package com.githunt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.github.ui.owner.details.OwnerDetailsViewModel
import com.github.ui.search.SearchViewModel

class MainActivity : ComponentActivity() {
    private val searchViewModel: SearchViewModel by viewModels()
    private val detailsViewModel: OwnerDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppContent(searchViewModel, detailsViewModel)
        }
    }
}