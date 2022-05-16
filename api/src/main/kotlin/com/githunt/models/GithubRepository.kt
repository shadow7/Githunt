package com.githunt.models

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GithubRepository(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("owner") val owner: GithubOwner,
    @SerialName("stargazers_count") val stars: Int,
    @SerialName("html_url") val htmlUrl: String,
    @SerialName("created_at") val createdAt: Instant,
    @SerialName("updated_at") val updatedAt: Instant,
)