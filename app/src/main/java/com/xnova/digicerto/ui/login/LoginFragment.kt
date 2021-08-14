package com.xnova.digicerto.ui.login

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xnova.digicerto.databinding.FragmentLoginBinding
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.xnova.digicerto.R
import com.xnova.digicerto.services.listeners.LoginListener

class LoginFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "BottomSheetTag"
    }

    private lateinit var mViewModel: LoginViewModel
    private lateinit var mRoot: ConstraintLayout
    private lateinit var mLoginListener: LoginListener
    private var mBinding: FragmentLoginBinding? = null

    private fun binding() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        mBinding = FragmentLoginBinding.inflate(inflater, container, false)
        mRoot = binding().root

        listeners()
        observers()

        return mRoot
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

    fun onAttach(loginListener: LoginListener) {
        mLoginListener = loginListener
    }

    private fun observers() {
        loginObserver()
    }

    private fun loginObserver() {
        mViewModel.login.observe(this, {
            if (it) {
                dismiss()
                mLoginListener.authenticate(true)
            } else {
                Toast.makeText(
                    requireContext(),
                    R.string.msg_incorrect_username_password,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
    }

    private fun listeners() {
        buttonLoginListener()
        editUsernameListener()
        editPasswordListener()
    }

    private fun buttonLoginListener() {
        binding().buttonLogin.setOnClickListener {
            val username = binding().editUsername.text.toString()
            val password = binding().editPassword.text.toString()

            if (loginValidate(username, password)) {
                mViewModel.login(username, password)
            }
        }
    }

    private fun editUsernameListener() {
        binding().editUsername.setOnClickListener {
            binding().inputUsername.isErrorEnabled = false
        }

        binding().editUsername.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding().inputUsername.isErrorEnabled = false
            }
        }
    }

    private fun editPasswordListener() {
        binding().editPassword.setOnClickListener {
            binding().inputPassword.isErrorEnabled = false
        }

        binding().editPassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding().inputPassword.isErrorEnabled = false
            }
        }
    }

    private fun loginValidate(username: String, password: String): Boolean {
        var valid = true

        if (username.isBlank()) {
            valid = false
            binding().inputUsername.error = getString(R.string.text_mandatory)
        } else {
            binding().inputUsername.isErrorEnabled = false
        }

        if (password.isBlank()) {
            valid = false
            binding().inputPassword.error = getString(R.string.text_mandatory)
        } else {
            binding().inputPassword.isErrorEnabled = false
        }

        return valid
    }

    private fun setExpandedState(dialog: Dialog) {
        if (dialog is BottomSheetDialog) {
            dialog.behavior.state = STATE_EXPANDED
        }
    }
}