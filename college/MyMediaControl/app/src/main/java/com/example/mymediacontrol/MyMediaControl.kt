package com.example.mymediacontrol

import android.content.Context
import android.provider.MediaStore


fun getDirList(context: Context) {
    var projection = arrayOf(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
    var cursor = context.contentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null,
        MediaStore.Images.ImageColumns.DISPLAY_NAME+"DESC")

    while ()
}