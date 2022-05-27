package com.github.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.githunt.models.GithubOwner
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchFeature(
    viewModel: SearchViewModel,
    openOrgDetails: (String) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        var searchQuery by rememberSaveable { mutableStateOf("") }

        SearchField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.updateSearchQuery(it)
            },
            onClick = { openOrgDetails(it) }
        )
        SearchList(viewModel) { openOrgDetails(it) }
    }
}

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(30),
        modifier = modifier
            .padding(10.dp)
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
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onClick(value) })
            )
            Box(Modifier.padding(5.dp)) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
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
    viewModel: SearchViewModel,
    onClick: (String) -> Unit
) {
    var usersList: List<GithubOwner> by rememberSaveable { mutableStateOf(emptyList()) }

    LaunchedEffect(viewModel.usersFlow) {
        viewModel.usersFlow.collect {
            usersList = it
        }
    }

    Box {
        LazyColumn {
            item { Spacer(Modifier.height(15.dp)) }
            items(usersList) { item ->
                Column(
                    Modifier
                        .clickable { onClick(item.name) }
                        .padding(8.dp)
                ) {
                    Text(item.name)
                }
            }
            item { Spacer(Modifier.height(70.dp)) }
        }
    }
}
