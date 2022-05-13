package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubSearchWrapper(
    @SerialName("items") val items: List<GithubRepository>
)