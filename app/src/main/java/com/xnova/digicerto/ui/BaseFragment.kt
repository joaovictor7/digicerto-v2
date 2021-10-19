package com.xnova.digicerto.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.xnova.digicerto.services.enums.AlertType
import com.xnova.digicerto.services.factories.AlertFactory
import com.xnova.digicerto.services.factories.ProgressBarFactory
import com.xnova.digicerto.services.factories.inputs.OnClickFactory

open class BaseFragment : Fragment() {

    protected lateinit var viewRoot: View
    private var mProgressBar: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return viewRoot
    }

    fun showAlert(alertType: AlertType, titleId: Int, messageId: Int) {
        AlertFactory(requireContext())
            .setType(alertType)
            .setTitle(titleId)
            .setMessage(messageId)
            .setNeutralButton()
            .show()
    }

    fun showProgressBar(messageId: Int) {
        mProgressBar = ProgressBarFactory(requireContext()).getInstance(messageId)
        mProgressBar!!.show()
    }

    fun hideProgressBar() {
        mProgressBar?.dismiss()
    }

    fun showSnackBar(message: String) {
        Snackbar.make(viewRoot, message, Snackbar.LENGTH_LONG).show()
    }

    fun showHelp(textView: TextView, messageId: Int) {
        textView.text = getString(messageId)
        textView.visibility = View.VISIBLE
    }

    fun hideHelp(textView: TextView) {
        textView.visibility = View.GONE
    }

    fun openActivity(classe: Class<*>) {
        startActivity(Intent(context, classe))
    }
}