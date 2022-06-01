package com.githunt.engine

import com.githunt.GithubApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit

val githubApi = createRestApi<GithubApi>(okHttpClient(), GithubApi.URL)

private fun okHttpClient(connectionTimeout: Long = 30L): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
        .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
        .readTimeout(connectionTimeout, TimeUnit.SECONDS)
        .build()

@OptIn(ExperimentalSerializationApi::class)
private inline fun <reified T : Any> createRestApi(
    client: OkHttpClient,
    baseUrl: String = "",
    json: Json = Json {
        ignoreUnknownKeys = true
    }
): T =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create()
