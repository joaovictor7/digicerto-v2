package com.xnova.digicerto.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.xnova.digicerto.services.enums.AlertType
import com.xnova.digicerto.services.factories.AlertFactory
import com.xnova.digicerto.services.factories.ProgressBarFactory

open class BaseActivity(title: Int? = null) : AppCompatActivity() {

    protected lateinit var alertFactory: AlertFactory
    protected lateinit var viewRoot: View

    private val mTitle = title
    private var mProgressBar: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar()

        alertFactory = AlertFactory(this)
    }

    fun showAlert(alertType: AlertType, titleId: Int, messageId: Int) {
        alertFactory.getInstance(alertType, titleId, messageId,
            neutralButton = { dialog, _ ->
                dialog.dismiss()
            }).show()
    }

    fun showSnackBar(messageId: Int) {
        Snackbar.make(viewRoot, messageId, Snackbar.LENGTH_LONG).show()
    }

    fun showMandatoryMsg(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun showHelp(textView: TextView, messageId: Int) {
        textView.text = getString(messageId)
        textView.visibility = View.VISIBLE
    }

    fun hideHelp(textView: TextView) {
        textView.visibility = View.GONE
    }

    fun showProgressBar(messageId: Int) {
        mProgressBar = ProgressBarFactory(this).getInstance(messageId)
        mProgressBar!!.show()
    }

    fun hideProgressBar() {
        mProgressBar?.dismiss()
    }

    private fun actionBar() {
        if (mTitle != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = getString(mTitle)
        }
    }
}