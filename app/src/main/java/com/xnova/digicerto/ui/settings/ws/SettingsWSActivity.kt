package com.xnova.digicerto.ui.settings.ws

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.xnova.digicerto.R
import com.xnova.digicerto.databinding.ActivitySettingsWsBinding
import com.xnova.digicerto.models.entities.settings.WSSettings
import com.xnova.digicerto.services.factories.AlertFactory
import com.xnova.digicerto.services.factories.inputs.OnClickFactory
import com.xnova.digicerto.services.factories.inputs.TextWatcherFactory
import com.xnova.digicerto.ui.BaseActivity

class SettingsWSActivity : BaseActivity() {

    private lateinit var mBinding: ActivitySettingsWsBinding
    private lateinit var mViewModel: SettingsWSViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProvider(this).get(SettingsWSViewModel::class.java)
        mBinding = ActivitySettingsWsBinding.inflate(layoutInflater)
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
                    showWSTestAlert()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun listeners() {
        mBinding.editHost.addTextChangedListener(TextWatcherFactory.cleanError(mBinding.inputHost))
        mBinding.editPort.addTextChangedListener(TextWatcherFactory.cleanError(mBinding.inputPort))
        mBinding.editUsername.addTextChangedListener(TextWatcherFactory.cleanError(mBinding.inputUsername))
        mBinding.editPassword.addTextChangedListener(TextWatcherFactory.cleanError(mBinding.inputPassword))
    }

    private fun observers() {
        mViewModel.refreshScreen.observe(this, {
            setComponents()
        })

        mViewModel.showAlert.observe(this, {
            showAlert(it.alertType, it.titleId, it.messageId)
        })

        mViewModel.showProgressBar.observe(this, {
            if (it) {
                showProgressBar(R.string.msg_testing_ws)
            } else {
                hideProgressBar()
            }
        })
    }

    private fun setComponents() {
        if (mViewModel.wsSettings != null) {
            mBinding.editHost.setText(mViewModel.wsSettings!!.host)
            mBinding.editPort.setText(mViewModel.wsSettings!!.port.toString())
            mBinding.editUsername.setText(mViewModel.wsSettings!!.username)
            mBinding.editPassword.setText(mViewModel.wsSettings!!.getPasswordDecrypted())
        }
    }

    private fun getComponents() {
        val host = mBinding.editHost.text.toString().trim()
        val port = mBinding.editPort.text.toString().toInt()
        val username = mBinding.editUsername.text.toString().trim()
        val password = mBinding.editPassword.text.toString().trim()

        val wsSettings = WSSettings(host, port, username)
        wsSettings.setPasswordDecrypted(password)
        mViewModel.wsSettings = wsSettings
    }

    private fun componentsValidate(): Boolean {
        var valid = true

        if (mBinding.editHost.text.isNullOrBlank()) {
            valid = false
            mBinding.inputHost.error = getString(R.string.text_mandatory)
        }

        if (mBinding.editPort.text.isNullOrBlank()) {
            valid = false
            mBinding.inputPort.error = getString(R.string.text_mandatory)
        }

        if (mBinding.editUsername.text.isNullOrBlank()) {
            valid = false
            mBinding.inputUsername.error = getString(R.string.text_mandatory)
        }

        if (mBinding.editPassword.text.isNullOrBlank()) {
            valid = false
            mBinding.inputPassword.error = getString(R.string.text_mandatory)
        }

        return valid
    }

    private fun showWSTestAlert() {
        AlertFactory(this)
            .setTitle(R.string.text_settings_saved)
            .setMessage(R.string.msg_test_ws)
            .setNegativeButton()
            .setPositiveButton { dialog, _ ->
                dialog.dismiss()
                mViewModel.testWS()
            }
            .show()
    }
}