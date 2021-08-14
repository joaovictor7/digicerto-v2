package com.xnova.digicerto.services.factories

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.xnova.digicerto.R
import com.xnova.digicerto.services.enums.AlertDialogType

class AlertDialogFactory(private val mContext: Context) {

    fun getInstance(type: AlertDialogType, titleId: Int, messageId: Int): AlertDialog {
        return when (type) {
            AlertDialogType.Error -> alertDialogBuild(
                R.drawable.ic_baseline_error_24,
                titleId,
                messageId
            )
            AlertDialogType.Info -> alertDialogBuild(
                R.drawable.ic_baseline_info_24,
                titleId,
                messageId
            )
            AlertDialogType.NetworkOff -> alertDialogBuild(
                R.drawable.ic_baseline_wifi_off_24,
                titleId,
                messageId
            )
            AlertDialogType.NetworkOn -> alertDialogBuild(
                R.drawable.ic_baseline_wifi_24,
                titleId,
                messageId
            )
        }


    }

    private fun alertDialogBuild(iconId: Int, titleId: Int, messageId: Int): AlertDialog {
        val builder = AlertDialog.Builder(mContext)
        builder.setMessage(messageId)
            .setIcon(iconId)
            .setTitle(titleId)
            .setNeutralButton(
                R.string.text_ok
            ) { dialog, _ ->
                dialog.dismiss()
            }

        return builder.create()
    }
}