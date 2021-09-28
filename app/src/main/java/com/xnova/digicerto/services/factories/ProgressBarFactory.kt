package com.xnova.digicerto.services.factories

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.xnova.digicerto.databinding.ProgressBarBinding

class ProgressBarFactory(context: Context) {

    private val mContext = context
    private var mMessageId: Int = 0

    fun getInstance(messageId: Int): AlertDialog {
        mMessageId = messageId
        return progressBarBuild()
    }

    private fun progressBarBuild(): AlertDialog {
        val alertDialog: AlertDialog.Builder = MaterialAlertDialogBuilder(mContext)
        alertDialog.setCancelable(false)

        val binding = ProgressBarBinding.inflate(LayoutInflater.from(mContext))
        binding.textAwait.setText(mMessageId)

        alertDialog.setView(binding.root)
        return alertDialog.create()
    }
}