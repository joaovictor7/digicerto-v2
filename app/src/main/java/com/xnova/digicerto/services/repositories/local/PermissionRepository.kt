package com.xnova.digicerto.services.repositories.local

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.xnova.digicerto.R
import com.xnova.digicerto.services.enums.AlertType
import com.xnova.digicerto.services.factories.AlertFactory

class PermissionRepository(context: Context) {

    private val mContext = context
    private val mDeniedPermissions = mutableListOf<Pair<String, Boolean>>()

    fun checkPermissions(vararg permissions: String) {
        if (!check(*permissions)) {
            permissionsRequest()
        }
    }

    private fun check(vararg permissions: String): Boolean {
        permissions.forEach {
            if (ContextCompat.checkSelfPermission(
                    mContext,
                    it
                ) == PackageManager.PERMISSION_DENIED
            ) {
                val showEducationalMsg =
                    (mContext as Activity).shouldShowRequestPermissionRationale(it)
                mDeniedPermissions.add(Pair(it, showEducationalMsg))
            }
        }

        return mDeniedPermissions.count() == 0
    }

    private fun permissionsRequest() {
        if (mDeniedPermissions.any { it.second }) {
            showEducationalMsg()
        }


    }

    private fun showEducationalMsg() {
        /*AlertFactory(mContext).getInstance(
            AlertType.Info, R.string.title_grant_permission, R.string.msg_grant_permission
        ).show()*/
    }
}