package com.github.ui.owner.details

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.githunt.models.GithubOwnerProject
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OwnerDetailsFeature(
    viewModel: OwnerDetailsViewModel,
    organizationName: String
) {
    Column(Modifier.fillMaxSize()) {
        OwnerProjectsList(viewModel, organizationName)
    }
}

@Composable
fun OwnerProjectsList(detailsViewModel: OwnerDetailsViewModel, organizationName: String) {
    var projectList: List<GithubOwnerProject> by remember { mutableStateOf(emptyList()) }

    LaunchedEffect(true) {
        detailsViewModel.updateSearchQuery(organizationName)
        detailsViewModel.organizationProjectsList.collectLatest {
            projectList = it
        }
    }

    val context = LocalContext.current

    LazyColumn {
        itemsIndexed(projectList) { index, item ->
            Column(
                Modifier
                    .clickable { context.openGithubTab(item.htmlUrl) }
                    .padding(8.dp)
            ) {
                Text(color = Color.Blue, text = "Item #$index")
                Text(color = Color.Blue, text = "$item")
            }
        }
    }
}

fun Context.openGithubTab(url: String) {
    CustomTabsIntent.Builder().build().run {
        launchUrl(this@openGithubTab, Uri.parse(url))
    }
}