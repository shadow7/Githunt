package com.github.ui.search

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun Search(viewModel: SearchViewModel) {
    Column {
        Button(
            onClick = { viewModel.getStarsList() },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Get Stars")
        }

        Surface(color = MaterialTheme.colors.background) {
            DisplayList(viewModel)
        }
    }
}

@Composable
fun DisplayList(viewModel: SearchViewModel) {
    val context = LocalContext.current
    LazyColumn {
        itemsIndexed(viewModel.movieListResponse) { index, item ->
            Column(
                Modifier.clickable { context.openGithubTab(item.htmlUrl) }
                    .padding(8.dp)) {
                Text(text = "Item #$index")
                Text(text = "$item")
            }
        }
    }
}

fun Context.openGithubTab(url: String) {
    CustomTabsIntent.Builder().build().run {
        launchUrl(this@openGithubTab, Uri.parse(url))
    }
}