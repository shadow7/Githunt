package com.githunt.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
@Parcelize
data class GithubOwner(
    @SerialName("id") val id: Int,
    @SerialName("login") val name: String,
    @SerialName("avatar_url") val avatar: String,
    @SerialName("html_url") val htmlUrl: String,
    @SerialName("type") val type: GithubUserType
) : Parcelable

@Serializable(with = GithubUserTypeSerializer::class)
@Parcelize
enum class GithubUserType(val typeName: String) : Parcelable {
    USER("User"),
    ORGANIZATION("Organization"),
}

object GithubUserTypeSerializer : KSerializer<GithubUserType> {
    override val descriptor: SerialDescriptor = serialDescriptor<String>()

    override fun serialize(encoder: Encoder, value: GithubUserType) {
        encoder.encodeString(value.toString().lowercase())
    }

    override fun deserialize(decoder: Decoder): GithubUserType {
        return GithubUserType.valueOf(decoder.decodeString().uppercase())
    }
}