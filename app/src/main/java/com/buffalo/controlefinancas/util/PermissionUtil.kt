package com.buffalo.controlefinancas.util

import android.app.Activity
import android.os.Build
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import java.lang.Exception
import java.util.ArrayList

object PermissionUtil {

    var PERMISSION_WRITE = 1001

    fun checkPermissions(
        context: Activity?,
        permissions: Array<String>,
        requestCode: Int
    ): Boolean {
        return try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return true
            }
            val permissionsToAsk: MutableList<String> = ArrayList()
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(
                        context!!,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    permissionsToAsk.add(permission)
                }
            }
            if (permissionsToAsk.size > 0) {
                ActivityCompat.requestPermissions(
                    context!!,
                    permissionsToAsk.toTypedArray(),
                    requestCode
                )
                return false
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}