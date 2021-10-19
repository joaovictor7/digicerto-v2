package com.xnova.digicerto.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.xnova.digicerto.services.enums.AlertType
import com.xnova.digicerto.services.factories.AlertFactory
import com.xnova.digicerto.services.factories.ProgressBarFactory
import com.xnova.digicerto.services.factories.inputs.OnClickFactory

open class BaseActivity : AppCompatActivity() {

    protected lateinit var viewRoot: View

    private var mProgressBar: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar()
    }

    fun showAlert(alertType: AlertType, titleId: Int, messageId: Int) {
        AlertFactory(this)
            .setType(alertType)
            .setTitle(titleId)
            .setMessage(messageId)
            .setNeutralButton()
            .show()
    }

    fun showSnackBar(message: String) {
        Snackbar.make(viewRoot, message, Snackbar.LENGTH_LONG).show()
    }

    fun showMandatoryMsg(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    fun showProgressBar(messageId: Int) {
        mProgressBar = ProgressBarFactory(this).getInstance(messageId)
        mProgressBar!!.show()
    }

    fun hideProgressBar() {
        mProgressBar?.dismiss()
    }

    fun setCloseKeyboardOnClick() {
        var viewGroup = viewRoot as ViewGroup
        if (viewGroup is ScrollView) {
            viewGroup = viewGroup.getChildAt(0) as ViewGroup
            if (viewGroup !is LinearLayoutCompat && viewGroup !is ConstraintLayout) {
                return
            }
        }

        val onClickFactory = OnClickFactory(this)
        viewGroup.setOnClickListener(onClickFactory.closeKeyboard(viewGroup))
        for (i in 0 until viewGroup.childCount) {
            val v = viewGroup.getChildAt(i)
            if (v is MaterialCardView) {
                v.setOnClickListener(onClickFactory.closeKeyboard(v))
            }
        }
    }

    private fun actionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}