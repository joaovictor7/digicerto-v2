package com.xnova.digicerto.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.xnova.digicerto.services.enums.AlertType
import com.xnova.digicerto.services.factories.AlertFactory
import com.xnova.digicerto.services.factories.ProgressBarFactory

open class BaseFragment : Fragment() {

    protected lateinit var viewRoot: View
    protected lateinit var alertFactory: AlertFactory

    private var mProgressBar: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        alertFactory = AlertFactory(requireContext())

        return viewRoot
    }

    fun showAlert(alertType: AlertType, titleId: Int, messageId: Int) {
        alertFactory.getInstance(alertType, titleId, messageId,
            neutralButton = { dialog, _ ->
                dialog.dismiss()
            }).show()
    }

    fun showProgressBar(messageId: Int) {
        mProgressBar = ProgressBarFactory(requireContext()).getInstance(messageId)
        mProgressBar!!.show()
    }

    fun hideProgressBar() {
        mProgressBar?.dismiss()
    }

    fun showHelp(textView: TextView, messageId: Int) {
        textView.text = getString(messageId)
        textView.visibility = View.VISIBLE
    }

    fun hideHelp(textView: TextView) {
        textView.visibility = View.GONE
    }
}