package com.abcloudz.notif.extensions

import android.content.Context
import android.net.Uri

fun Context.generateUri(resource: Int): Uri {
    return Uri.parse(String.format("android.resource://%s/%s", packageName, resource))
}