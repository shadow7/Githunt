package com.githunt.common

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

fun Context.openGithubTab(url: String) {
    CustomTabsIntent.Builder().build().run {
        launchUrl(this@openGithubTab, Uri.parse(url))
    }
}