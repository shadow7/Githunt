package com.githunt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.github.ui.search.Search
import com.github.ui.search.SearchViewModel
import com.githunt.ui.theme.GithuntTheme

class MainActivity : ComponentActivity() {
    private val searchViewModel: SearchViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GithuntTheme(true) {
                // A surface container using the 'background' color from the theme
                Search(searchViewModel)
            }
        }
    }
}