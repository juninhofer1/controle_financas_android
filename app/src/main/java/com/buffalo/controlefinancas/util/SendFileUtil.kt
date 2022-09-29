package com.buffalo.controlefinancas.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.core.content.FileProvider
import java.io.File


fun Activity.shareFile(path: File) {
    Handler(Looper.myLooper()!!).post {
        try {
            val FILE_PROVIDER_AUTHORITY =  "${this.packageName}.fileprovider"
            val contentUri = FileProvider.getUriForFile(applicationContext, FILE_PROVIDER_AUTHORITY, path)
            if (contentUri != null) {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                shareIntent.type = "*/*"
                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
                startActivity(Intent.createChooser(shareIntent, "Compartilhar arquivo"))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}
