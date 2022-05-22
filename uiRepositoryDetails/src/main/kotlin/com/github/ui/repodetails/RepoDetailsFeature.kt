package com.github.ui.repodetails

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.githunt.models.GithubOwnerProject

@Composable
fun RepositoryDetails(repoList: List<GithubOwnerProject>) {
    Surface(color = MaterialTheme.colors.background) {
        OwnerRepositoriesList(repoList)
    }
}

@Composable
fun OwnerRepositoriesList(repoList: List<GithubOwnerProject>) {
    LazyColumn {
        itemsIndexed(repoList) { index, item ->
            Column(
                Modifier
                    .clickable { LocalContext.current.openGithubTab(item.htmlUrl) }
                    .padding(8.dp)
            ) {
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