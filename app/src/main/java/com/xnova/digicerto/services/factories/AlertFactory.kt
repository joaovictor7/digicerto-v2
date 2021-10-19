package com.xnova.digicerto.services.factories

import android.content.Context
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.xnova.digicerto.R
import com.xnova.digicerto.services.enums.AlertType

class AlertFactory(context: Context) {

    companion object {
        private val mNeutralButton: (dialog: DialogInterface, id: Int) -> Unit =
            { dialog, _ -> dialog.dismiss() }

        private val mNegativeButton: (dialog: DialogInterface, id: Int) -> Unit =
            { dialog, _ -> dialog.dismiss() }

        private val mCancelable: () -> Unit = {}
    }

    private var mAlertBuilder = MaterialAlertDialogBuilder(context)

    fun setType(type: AlertType): AlertFactory {
        val iconId = when (type) {
            AlertType.Error -> R.drawable.ic_baseline_error_24
            AlertType.Info -> R.drawable.ic_baseline_info_24
            AlertType.NetworkOff -> R.drawable.ic_baseline_wifi_off_24
            AlertType.NetworkOn -> R.drawable.ic_baseline_wifi_24
            AlertType.Success -> R.drawable.ic_baseline_check_circle_24
        }

        mAlertBuilder = mAlertBuilder.setIcon(iconId)
        return this
    }

    fun setTitle(titleId: Int): AlertFactory {
        mAlertBuilder = mAlertBuilder.setTitle(titleId)
        return this
    }

    fun setMessage(messageId: Int): AlertFactory {
        mAlertBuilder = mAlertBuilder.setMessage(messageId)
        return this
    }

    fun setCancelable(cancel: () -> Unit = mCancelable): AlertFactory {
        mAlertBuilder = mAlertBuilder.setOnCancelListener { cancel() }
        return this
    }

    fun setCancelable(cancel: Boolean): AlertFactory {
        mAlertBuilder = mAlertBuilder.setCancelable(cancel)
        return this
    }

    fun setNeutralButton(
        neutralButton: (dialog: DialogInterface, id: Int) -> Unit = mNeutralButton
    ): AlertFactory {
        mAlertBuilder = mAlertBuilder.setNeutralButton(R.string.text_ok, neutralButton)
        return this
    }

    fun setPositiveButton(
        textIdPositive: Int = R.string.text_yes,
        positiveButton: (dialog: DialogInterface, any: Int) -> Unit
    ): AlertFactory {
        mAlertBuilder = mAlertBuilder.setPositiveButton(textIdPositive, positiveButton)
        return this
    }

    fun setNegativeButton(
        textIdNegative: Int = R.string.text_no,
        negativeButton: (dialog: DialogInterface, any: Int) -> Unit = mNegativeButton
    ): AlertFactory {
        mAlertBuilder = mAlertBuilder.setNegativeButton(textIdNegative, negativeButton)
        return this
    }

    fun show() {
        mAlertBuilder.create().show()
    }
}