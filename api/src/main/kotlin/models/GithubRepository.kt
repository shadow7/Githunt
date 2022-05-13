package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import serializers.GithubDateTimeV3Serializer
import java.time.LocalDateTime

@Serializable
data class GithubRepository(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("owner") val owner: GithubOwner,
    @SerialName("stargazers_count") val stars: Int,
    @SerialName("html_url") val htmlUrl: String,

    @Serializable(with = GithubDateTimeV3Serializer::class)
    @SerialName("created_at") val createdAt: LocalDateTime,

    @Serializable(with = GithubDateTimeV3Serializer::class)
    @SerialName("updated_at") val updatedAt: LocalDateTime,
)