package com.sarftec.dogs.view.model

import android.net.Uri

class ImageInfo(
    val uri: Uri,
    val name: String,
    val extension: String
) {
    fun toFullName() : String = "$name.$extension"
}