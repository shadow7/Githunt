package com.github.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun Greeting(viewModel: SearchViewModel) {
    Column {
        DisplayData(viewModel)
    }
}

@Composable
fun DisplayData(viewModel: SearchViewModel) {
    Button({
        viewModel.getStarsList()
    }) {
        Text("Get Stars")
    }

    Text(viewModel.movieListResponse.toString())
}