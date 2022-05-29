package com.github.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.githunt.models.GithubOwner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchFeature(
    query: String,
    ownerFlow: Flow<List<GithubOwner>>,
    updateSearchQuery: (String) -> Unit = {},
    openOrgDetails: (String, String) -> Unit = { _, _ -> }
) {
    var searchQuery by rememberSaveable { mutableStateOf(query) }
    var ownerList: List<GithubOwner> by rememberSaveable { mutableStateOf(emptyList()) }

    LaunchedEffect(ownerFlow) {
        ownerFlow.collectLatest {
            ownerList = it
        }
    }

    Column(Modifier.fillMaxSize()) {
        SearchField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                updateSearchQuery(it)
            },
            onClick = {
                val owner = ownerList.firstOrNull { it.name == searchQuery }
                if (owner == null) {
                    openOrgDetails(searchQuery, "")
                } else {
                    openOrgDetails(owner.name, owner.avatar)
                }
            }
        )
        SearchList(ownerList) { openOrgDetails(it.name, it.avatar) }
    }
}

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(30),
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(50.dp),
        elevation = 6.dp,
        backgroundColor = Color.White
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.padding(start = 12.dp)
        ) {
            BasicTextField(
                value = value,
                onValueChange = { onValueChange(it) },
                Modifier.weight(1f),
                textStyle = TextStyle(fontSize = 20.sp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onClick(value) })
            )
            Box(Modifier.padding(5.dp)) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    Modifier
                        .clip(RoundedCornerShape(50))
                        .padding(5.dp)
                )
            }
        }
    }
}

@Composable
fun SearchList(
    usersList: List<GithubOwner>,
    onClick: (GithubOwner) -> Unit
) {
    if (usersList.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(10)
                )
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item {
                Text(
                    text = "Organizations:",
                    modifier = Modifier.padding(start = 12.dp, bottom = 8.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            items(usersList) { item ->
                Column(
                    Modifier
                        .clickable { onClick(item) }
                        .padding(16.dp)
                ) {
                    Text(
                        text = item.name,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
            item { ListFooter() }
        }
    }
}

@Composable
fun ListFooter() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding()
    ) {
        Text(
            text = "— End of results —",
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.Center),
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SearchFeature("nytimes", flowOf(listOf(GithubOwner(name = "nytimes"))), {}) { _, _ -> }
}
