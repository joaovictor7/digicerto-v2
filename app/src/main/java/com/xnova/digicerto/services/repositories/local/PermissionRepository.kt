package com.xnova.digicerto.services.repositories.local

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import com.xnova.digicerto.R
import com.xnova.digicerto.services.enums.AlertType
import com.xnova.digicerto.services.factories.AlertFactory

class PermissionRepository(context: Context) {

    private val mContext = context

    fun check(
        permissions: Array<String>,
        result: ActivityResultLauncher<Array<String>>,
        postAction: () -> Unit
    ) {
        val deniedPermissions = mutableListOf<Pair<String, Boolean>>()
        permissions.forEach {
            if (
                ContextCompat.checkSelfPermission(mContext, it) == PackageManager.PERMISSION_DENIED
            ) {
                val showEducationalMsg =
                    (mContext as Activity).shouldShowRequestPermissionRationale(it)
                deniedPermissions.add(Pair(it, showEducationalMsg))
            }
        }

        if (deniedPermissions.isEmpty()) {
            postAction()
        } else {
            necessaryShowEducationalAlert(permissions, deniedPermissions, result)
        }
    }

    private fun necessaryShowEducationalAlert(
        permissions: Array<String>,
        deniedPermissions: MutableList<Pair<String, Boolean>>,
        intent: ActivityResultLauncher<Array<String>>
    ) {
        if (deniedPermissions.any { it.second }) {
            showEducationalAlert(permissions, intent)
        } else {
            intent.launch(permissions)
        }
    }

    private fun showEducationalAlert(
        permissions: Array<String>,
        intent: ActivityResultLauncher<Array<String>>
    ) {
        AlertFactory(mContext)
            .setType(AlertType.Info)
            .setTitle(R.string.text_grant_permission)
            .setMessage(R.string.msg_grant_permission)
            .setNeutralButton { _, _ ->
                intent.launch(permissions)
            }
            .setCancelable {
                intent.launch(permissions)
            }
            .show()
    }
}