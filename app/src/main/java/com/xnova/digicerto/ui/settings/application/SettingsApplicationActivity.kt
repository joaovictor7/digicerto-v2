package com.xnova.digicerto.ui.settings.application

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.xnova.digicerto.R
import com.xnova.digicerto.databinding.ActivitySettingsApplicationBinding
import com.xnova.digicerto.services.enums.settings.OperationType
import com.xnova.digicerto.services.factories.inputs.OnClickFactory
import com.xnova.digicerto.services.factories.inputs.TextWatcherFactory
import com.xnova.digicerto.services.util.StringUtil
import com.xnova.digicerto.ui.BaseActivity

class SettingsApplicationActivity : BaseActivity() {

    private lateinit var mBinding: ActivitySettingsApplicationBinding
    private lateinit var mViewModel: SettingsApplicationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProvider(this).get(SettingsApplicationViewModel::class.java)
        mBinding = ActivitySettingsApplicationBinding.inflate(layoutInflater)
        viewRoot = mBinding.root
        setContentView(viewRoot)
        setCloseKeyboardOnClick()

        listeners()
        observers()
        setComponents()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.menu_save -> {
                if (componentsValidate()) {
                    getComponents()
                    mViewModel.save()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun listeners() {
        mBinding.editEmail.addTextChangedListener(TextWatcherFactory.cleanError(mBinding.inputEmail))
    }

    private fun observers() {
        mViewModel.refreshScreen.observe(this, {
            setComponents()
        })

        mViewModel.saveMsg.observe(this, {
            showSnackBar(getString(it))
        })
    }

    private fun setComponents() {
        mBinding.editEmail.setText(mViewModel.settings.email)

        when (mViewModel.settings.operationType) {
            OperationType.FTP -> mBinding.radioFTP.isChecked = true
            OperationType.WebService -> mBinding.radioWS.isChecked = true
        }
    }

    private fun getComponents() {
        val email = mBinding.editEmail.text.toString().trim()
        val operationType = when {
            mBinding.radioWS.isChecked -> OperationType.WebService
            else -> OperationType.FTP
        }

        mViewModel.setSettings(operationType, email)
    }

    private fun componentsValidate(): Boolean {
        var valid = true

        if (!mBinding.editEmail.text.isNullOrBlank() &&
            !StringUtil.emailValid(mBinding.editEmail.text.toString())
        ) {
            valid = false
            mBinding.inputEmail.error = getString(R.string.text_incorrect_email)
        }

        return valid
    }
}