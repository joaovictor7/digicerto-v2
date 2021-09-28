package com.xnova.digicerto.ui.login

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xnova.digicerto.R
import com.xnova.digicerto.databinding.FragmentLoginBinding
import com.xnova.digicerto.services.enums.AlertType
import com.xnova.digicerto.services.factories.AlertFactory
import com.xnova.digicerto.services.factories.inputs.OnClickFactory
import com.xnova.digicerto.services.factories.inputs.TextWatcherFactory
import com.xnova.digicerto.services.listeners.LoginListener

class LoginFragment : BottomSheetDialogFragment(), View.OnClickListener {

    companion object {
        const val TAG = "BottomSheetTag"
    }

    private var mBinding: FragmentLoginBinding? = null
    private lateinit var mViewModel: LoginViewModel
    private lateinit var mLoginListener: LoginListener
    private lateinit var mAlertFactory: AlertFactory

    private fun binding() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        mBinding = FragmentLoginBinding.inflate(inflater, container, false)

        mAlertFactory = AlertFactory(requireContext())

        listeners()
        observers()
        necessaryFirstRegister()

        return binding().root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        setExpandedState(dialog)
        return dialog
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        view?.let {
            Toast.makeText(context, R.string.text_unauthorized, Toast.LENGTH_LONG).show()
            mLoginListener.authenticate(false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    override fun onClick(v: View) {
        when (v) {
            binding().buttonLogin -> {
                val username = binding().editUsername.text.toString()
                val password = binding().editPassword.text.toString()

                if (loginValidate(username, password)) {
                    mViewModel.login(username, password)
                }
            }
        }
    }

    fun onAttach(loginListener: LoginListener) {
        mLoginListener = loginListener
    }

    private fun observers() {
        mViewModel.login.observe(this, {
            if (it) {
                dismiss()
                mLoginListener.authenticate(true)
            } else {
                Toast.makeText(
                    requireContext(), R.string.msg_incorrect_username_password, Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun listeners() {
        binding().buttonLogin.setOnClickListener(this)
        binding().editUsername.addTextChangedListener(TextWatcherFactory.cleanError(binding().inputUsername))
        binding().editPassword.addTextChangedListener(TextWatcherFactory.cleanError(binding().inputPassword))

        val onClickFactory = OnClickFactory(requireContext())
        binding().root.setOnClickListener(onClickFactory.closeKeyboard(binding().root))
    }

    private fun loginValidate(username: String, password: String): Boolean {
        var valid = true

        if (username.isBlank()) {
            valid = false
            binding().inputUsername.error = getString(R.string.text_mandatory)
        }

        if (password.isBlank()) {
            valid = false
            binding().inputPassword.error = getString(R.string.text_mandatory)
        }

        return valid
    }


    private fun setExpandedState(dialog: Dialog) {
        if (dialog is BottomSheetDialog) {
            dialog.behavior.state = STATE_EXPANDED
        }
    }

    private fun necessaryFirstRegister() {
        if (!mViewModel.loginAvailable()) {
            showAlert(R.string.text_access_denied, R.string.msg_first_register)
            dismiss()
        }
    }

    private fun showAlert(titleId: Int, messageId: Int) {
        mAlertFactory.getInstance(AlertType.Info, titleId, messageId,
            neutralButton = { dialog, _ ->
                dialog.dismiss()
                mLoginListener.authenticate(false)
            }).show()
    }
}