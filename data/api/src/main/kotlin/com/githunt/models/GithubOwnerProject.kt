package com.githunt.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
@TypeParceler<Instant, InstantClassParceler>()
data class GithubOwnerProject(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int = 0,
    @SerialName("owner") val owner: GithubOwner = GithubOwner(name),
    @SerialName("stargazers_count") val stars: Int = 1,
    @SerialName("html_url") val htmlUrl: String = "",
    @SerialName("created_at") val createdAt: Instant = Clock.System.now(),
    @SerialName("updated_at") val updatedAt: Instant = Clock.System.now(),
) : Parcelable

object InstantClassParceler : Parceler<Instant> {
    override fun create(parcel: Parcel) = Instant.fromEpochMilliseconds(parcel.readLong())

    override fun Instant.write(parcel: Parcel, flags: Int) {
        parcel.writeLong(toEpochMilliseconds())
    }
}
