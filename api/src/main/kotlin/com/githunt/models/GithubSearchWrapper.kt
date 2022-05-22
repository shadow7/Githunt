package com.githunt.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubRepositoryWrapper(
    @SerialName("items") val items: List<GithubOwnerProject>
)

@Serializable
data class GithubOwnersWrapper(
    @SerialName("items") val items: List<GithubOwner>
)