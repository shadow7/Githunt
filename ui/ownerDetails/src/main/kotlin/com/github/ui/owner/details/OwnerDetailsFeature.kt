package com.github.ui.owner.details

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.githunt.models.GithubOwnerProject

@Composable
fun OwnerDetailsFeature(
    viewModel: OwnerDetailsViewModel,
    ownerName: String,
    ownerAvatar: String,
) {
    var projectList: List<GithubOwnerProject> by remember { mutableStateOf(emptyList()) }

    LaunchedEffect(ownerName) {
        projectList = viewModel.organizationRepos(ownerName)
    }

    OwnerDetailsTheme {
        OwnerProjectsList(ownerAvatar, projectList)
    }
}

@Composable
private fun DisplayAvatar(
    ownerAvatar: String
) {
    var imageBackground: Color by remember { mutableStateOf(Color.Gray) }
    Box(
        modifier = Modifier
            .background(imageBackground)
            .fillMaxWidth(1f)
            .height(250.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(ownerAvatar)
                .allowHardware(false)
                .allowConversionToBitmap(true)
                .listener(onSuccess = { _, result ->
                    Palette.from(result.drawable.toBitmap(config = Bitmap.Config.RGBA_F16))
                        .maximumColorCount(16)
                        .generate {
                            imageBackground = Color(
                                it?.lightMutedSwatch?.rgb
                                    ?: it?.mutedSwatch?.rgb
                                    ?: Color.Blue.toArgb()
                            )
                        }
                })
                .crossfade(true)
                .crossfade(600)
                .build(),
            contentDescription = "Owner Avatar",
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.Center),
            placeholder = rememberVectorPainter(image = Icons.Default.Person),
            error = rememberVectorPainter(image = Icons.Default.Person),
        )
    }
}

@Composable
fun OwnerProjectsList(ownerAvatar: String, projectList: List<GithubOwnerProject>) {
    val cellCount = 2
    val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(cellCount) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(cellCount),
        modifier = Modifier.fillMaxWidth()
    ) {
        item(span = span) { DisplayAvatar(ownerAvatar) }
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
        else -> Color.DarkGray
    }

    Card(
        shape = RoundedCornerShape(15),
        modifier = Modifier
            .clickable { context.openGithubTab(item.htmlUrl) }
            .padding(8.dp),
        elevation = 6.dp
    ) {
        Column(
            modifier = Modifier
                .background(boxColor)
                .padding(16.dp)
        ) {
            Text(
                text = item.name,
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 20.sp,
                overflow = TextOverflow.Ellipsis
            )
            Row {
                Text(
                    text = item.stars.toString(),
                    modifier = Modifier.padding(top = 3.dp, end = 4.dp)
                )
                Icon(Icons.Default.Star, contentDescription = "Github stars")
            }
        }
    }
}

fun Context.openGithubTab(url: String) {
    CustomTabsIntent.Builder().build().run {
        launchUrl(this@openGithubTab, Uri.parse(url))
    }
}