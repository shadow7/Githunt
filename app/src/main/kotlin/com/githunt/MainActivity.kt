package com.githunt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.github.ui.search.Greeting
import com.github.ui.search.SearchViewModel
import com.githunt.ui.theme.GithuntTheme

class MainActivity : ComponentActivity() {
    private val searchViewModel = SearchViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GithuntTheme(true) {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting(searchViewModel)
                }
            }
        }
    }
}