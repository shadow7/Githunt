package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubOwner(
    @SerialName("login") val login: String,
    @SerialName("avatar_url") val avatar: String,
    @SerialName("html_url") val htmlUrl: String,
)