package com.github.ui.owner.details

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.githunt.models.GithubOwnerProject

@Composable
fun OwnerDetailsFeature(
    viewModel: OwnerDetailsViewModel,
    ownerName: String,
    ownerAvatar: String,
) {
    Log.e("Avatar", ownerAvatar)
    var projectList: List<GithubOwnerProject> by remember { mutableStateOf(emptyList()) }

    LaunchedEffect(ownerName) {
        projectList = viewModel.organizationRepos(ownerName)
    }

    OwnerDetailsTheme {
        Column(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxWidth(1f)
                    .fillMaxHeight(.3f)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(ownerAvatar)
                        .crossfade(true)
                        .crossfade(600)
                        .build(),
                    contentDescription = "Owner Avatar",
                    modifier = Modifier
                        .fillMaxHeight()
                        .align(Alignment.Center),
                    placeholder = rememberVectorPainter(image = Icons.Default.Person),
                    error = rememberVectorPainter(image = Icons.Default.Person),
                    onLoading = { Log.e("$this", "loading") },
                    onError = { Log.e("$this", "error ${it.result.throwable}") },
                    onSuccess = { Log.e("$this", "success") }
                )
            }
            OwnerProjectsList(projectList)
        }
    }
}

@Composable
fun OwnerProjectsList(projectList: List<GithubOwnerProject>) {
    LazyColumn {
        itemsIndexed(projectList) { index, item ->
            DetailsItem(index, item)
        }
    }
}

@Composable
private fun DetailsItem(
    index: Int,
    item: GithubOwnerProject
) {
    val context = LocalContext.current

    val boxColor = when (index) {
        0 -> OwnerDetailsTheme.colors.gold
        1 -> OwnerDetailsTheme.colors.silver
        2 -> OwnerDetailsTheme.colors.bronze
        else -> Color.White
    }

    Box(
        Modifier
            .background(boxColor)
            .clickable { context.openGithubTab(item.htmlUrl) }
            .padding(8.dp)
    ) {
        Column {
            Text(text = item.name)
            Text(text = "Stars: ${item.stars}")
        }
    }
}

fun Context.openGithubTab(url: String) {
    CustomTabsIntent.Builder().build().run {
        launchUrl(this@openGithubTab, Uri.parse(url))
    }
}