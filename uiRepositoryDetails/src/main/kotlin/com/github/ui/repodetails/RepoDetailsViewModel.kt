package com.github.ui.repodetails

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githunt.engine.githubApi
import com.githunt.models.GithubOwnerProject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepoDetailsViewModel(state: SavedStateHandle) : ViewModel() {

    private val tag = this::class.simpleName

    var organizationReposList: List<GithubOwnerProject> by mutableStateOf(listOf())
        private set

    var errorMessage: String by mutableStateOf("")

    fun getOrganizationsRepositoryList(organization:String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val wrapper = githubApi.getOrganizationsRepos(organization)
                organizationReposList = wrapper.items
                Log.i(tag, "\nGet Stars:\n")
                Log.i(tag, wrapper.items.toString())
            } catch (e: Exception) {
                Log.e(tag, "getStarsList() crash", e)
                errorMessage = e.message.toString()
            }
        }
    }
}
