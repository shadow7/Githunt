package com.github.ui.owner.details

import android.content.Context
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.githunt.models.GithubOwnerProject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf

@Composable
fun OwnerDetailsFeature(
    viewModel: OwnerDetailsViewModel,
    ownerName: String,
    ownerAvatar: String,
    openChromeTab: (Context, String) -> Unit
) {
    OwnerDetails(
        ownerName,
        ownerAvatar,
        openChromeTab,
        { viewModel.organizationRepos(it) },
        viewModel.imageBackgroundFlow,
        { viewModel.updateResults(it) },
        false
    )
}

@Composable
fun OwnerDetails(
    ownerName: String,
    ownerAvatar: String,
    openChromeTab: (Context, String) -> Unit,
    projectsFlow: suspend (String) -> Flow<List<GithubOwnerProject>>,
    imageBackgroundFlow: Flow<Color>,
    avatarEvent: (SuccessResult) -> Unit,
    isPreview: Boolean
) {
    OwnerDetailsTheme {
        var projectsList: List<GithubOwnerProject> by rememberSaveable { mutableStateOf(emptyList()) }

        LaunchedEffect(projectsFlow) {
            projectsFlow(ownerName).collectLatest {
                projectsList = it
            }
        }

        OwnerProjectsList(
            ownerAvatar,
            projectsList,
            openChromeTab,
            imageBackgroundFlow,
            avatarEvent,
            isPreview
        )
    }
}

@Composable
fun OwnerProjectsList(
    ownerAvatar: String,
    projectList: List<GithubOwnerProject>,
    openChromeTab: (Context, String) -> Unit,
    imageBackgroundFlow: Flow<Color>,
    avatarEvent: (SuccessResult) -> Unit,
    isPreview: Boolean,
) {
    val cellCount = 2
    val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(cellCount) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(cellCount),
        modifier = Modifier.fillMaxWidth()
    ) {
        item(span = span) {
            DisplayAvatar(
                ownerAvatar,
                imageBackgroundFlow,
                avatarEvent,
                isPreview
            )
        }
        item(span = span) { Spacer(modifier = Modifier.height(8.dp)) }
        itemsIndexed(projectList) { index, item ->
            DetailsItem(index, item, openChromeTab)
        }
    }
}

@Composable
private fun AvatarPlaceholder() {
    Icon(Icons.Default.Person, "", modifier = Modifier.fillMaxSize())
}

@Composable
private fun Avatar(
    ownerAvatar: String,
    imageBackgroundFlow: Flow<Color>,
    avatarEvent: (SuccessResult) -> Unit
) {
    var imageBackground: Color by remember { mutableStateOf(Color.Gray) }

    LaunchedEffect(imageBackground) {
        imageBackgroundFlow.collectLatest {
            imageBackground = it
        }
    }
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
                .listener(onSuccess = { _, result -> avatarEvent(result) })
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
private fun DisplayAvatar(
    ownerAvatar: String,
    imageBackgroundFlow: Flow<Color>,
    avatarEvent: (SuccessResult) -> Unit,
    isPreview: Boolean
) {
    if (isPreview) {
        Box(
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth(1f)
                .height(250.dp)
        ) {
            AvatarPlaceholder()
        }
    } else {
        Avatar(
            ownerAvatar = ownerAvatar,
            imageBackgroundFlow = imageBackgroundFlow,
            avatarEvent = avatarEvent
        )
    }
}

@Composable
private fun DetailsItem(
    index: Int,
    item: GithubOwnerProject,
    openChromeTab: (Context, String) -> Unit
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
            .clickable { openChromeTab(context, item.htmlUrl) }
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

@Preview(showBackground = true)
@Composable
fun DetailsPreview() {
    OwnerDetails(
        "square",
        "https://avatars.githubusercontent.com/u/82592",
        { _, _ -> },
        {
            flowOf(
                listOf(
                    GithubOwnerProject(name = "okhttp", stars = 3),
                    GithubOwnerProject(name ="retrofit",stars = 2),
                    GithubOwnerProject(name ="okio",stars = 1)
                )
            )
        },
        flowOf(Color.Gray),
        {},
        true
    )
}