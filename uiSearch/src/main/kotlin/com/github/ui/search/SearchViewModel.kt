package com.github.ui.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import engine.githubApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import models.GithubRepository

class SearchViewModel : ViewModel() {
    var movieListResponse: List<GithubRepository> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")
    fun getStarsList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val wrapper = githubApi.getTopStarredRepos("square")
                movieListResponse = wrapper.items
                Log.i("ViewModel", "\nGet Stars:\n")
                Log.i("ViewModel", wrapper.items.toString())
            } catch (e: Exception) {
                Log.e("ViewModel", "crash", e)
                errorMessage = e.message.toString()
            }
        }
    }
}