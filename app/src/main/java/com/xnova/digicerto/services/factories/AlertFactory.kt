package com.xnova.digicerto.services.factories

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.xnova.digicerto.R
import com.xnova.digicerto.services.enums.AlertType

class AlertFactory(context: Context) {

    private val mContext = context

    fun getInstance(
        type: AlertType,
        titleId: Int,
        messageId: Int,
        neutralButton: (dialog: DialogInterface, id: Int) -> Unit
    ): AlertDialog {
        val iconId = when (type) {
            AlertType.Error -> R.drawable.ic_baseline_error_24
            AlertType.Info -> R.drawable.ic_baseline_info_24
            AlertType.NetworkOff -> R.drawable.ic_baseline_wifi_off_24
            AlertType.NetworkOn -> R.drawable.ic_baseline_wifi_24
            AlertType.Success -> R.drawable.ic_baseline_check_circle_24
        }

        val builder = MaterialAlertDialogBuilder(mContext)
        builder.setMessage(messageId)
            .setIcon(iconId)
            .setTitle(titleId)
            .setNeutralButton(R.string.text_ok) { dialog, id ->
                neutralButton(dialog, id)
            }

        return builder.create()
    }

    fun getInstance(
        titleId: Int,
        messageId: Int,
        actionPositive: (dialog: DialogInterface, any: Int) -> Unit,
        actionNegative: (dialog: DialogInterface, any: Int) -> Unit,
        textIdPositive: Int = R.string.text_yes,
        textIdNegative: Int = R.string.text_no
    ): AlertDialog {
        val builder = MaterialAlertDialogBuilder(mContext)
        builder.setCancelable(false)
            .setTitle(titleId)
            .setMessage(messageId)
            .setPositiveButton(textIdPositive) { dialog, id ->
                actionPositive(dialog, id)
            }
            .setNegativeButton(textIdNegative) { dialog, id ->
                actionNegative(dialog, id)
            }

        return builder.create()
    }
}