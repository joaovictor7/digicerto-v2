package com.xnova.digicerto.ui.settings.ftp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.xnova.digicerto.R
import com.xnova.digicerto.databinding.ActivityFtpSettingsBinding
import com.xnova.digicerto.models.entities.settings.FTPSettings
import com.xnova.digicerto.services.enums.AlertType
import com.xnova.digicerto.services.factories.AlertFactory
import com.xnova.digicerto.services.factories.inputs.OnClickFactory
import com.xnova.digicerto.services.factories.ProgressBarFactory
import com.xnova.digicerto.services.factories.inputs.TextWatcherFactory

class FTPSettingsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityFtpSettingsBinding
    private lateinit var mViewModel: FTPSettingsViewModel
    private lateinit var mAlertFactory: AlertFactory
    private var mProgressBar: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProvider(this).get(FTPSettingsViewModel::class.java)
        mBinding = ActivityFtpSettingsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mAlertFactory = AlertFactory(this)

        actionBar()
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
                    showFTPTestAlert()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun actionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.text_FTP)
    }

    private fun listeners() {
        mBinding.editHost.addTextChangedListener(TextWatcherFactory.cleanError(mBinding.inputHost))
        mBinding.editPort.addTextChangedListener(TextWatcherFactory.cleanError(mBinding.inputPort))
        mBinding.editUsername.addTextChangedListener(TextWatcherFactory.cleanError(mBinding.inputUsername))
        mBinding.editPassword.addTextChangedListener(TextWatcherFactory.cleanError(mBinding.inputPassword))
        mBinding.editFolder.addTextChangedListener(TextWatcherFactory.cleanError(mBinding.inputFolder))

        val onClickFactory = OnClickFactory(this)
        mBinding.root.setOnClickListener(onClickFactory.closeKeyboard(mBinding.root))
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
                showProgressBar(R.string.msg_testing_ftp)
            } else {
                hideProgressBar()
            }
        })
    }

    private fun setComponents() {
        if (mViewModel.ftpSettings != null) {
            mBinding.editHost.setText(mViewModel.ftpSettings!!.host)
            mBinding.editPort.setText(mViewModel.ftpSettings!!.port.toString())
            mBinding.editUsername.setText(mViewModel.ftpSettings!!.username)
            mBinding.editPassword.setText(mViewModel.ftpSettings!!.getPasswordDecrypted())
            mBinding.editFolder.setText(mViewModel.ftpSettings!!.folder)
        }
    }

    private fun getComponents() {
        val host = mBinding.editHost.text.toString().trim()
        val port = mBinding.editPort.text.toString().toInt()
        val username = mBinding.editUsername.text.toString().trim()
        val password = mBinding.editPassword.text.toString().trim()
        val folder = mBinding.editFolder.text.toString().trim()

        val ftpSettings = FTPSettings(host, port, username)
        ftpSettings.setPasswordDecrypted(password)
        ftpSettings.setFormattedFolder(folder)
        mViewModel.ftpSettings = ftpSettings
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

        if (mBinding.editFolder.text.isNullOrBlank()) {
            valid = false
            mBinding.inputFolder.error = getString(R.string.text_mandatory)
        }

        return valid
    }

    private fun showProgressBar(messageId: Int) {
        mProgressBar = ProgressBarFactory(this).getInstance(messageId)
        mProgressBar!!.show()
    }

    private fun hideProgressBar() {
        mProgressBar?.dismiss()
    }

    private fun showAlert(alertType: AlertType, titleId: Int, messageId: Int) {
        mAlertFactory.getInstance(alertType, titleId, messageId,
            neutralButton = { dialog, _ ->
                dialog.dismiss()
            }).show()
    }

    private fun showFTPTestAlert() {
        mAlertFactory.getInstance(R.string.text_settings_saved, R.string.msg_test_ftp,
            actionPositive = { dialog, _ ->
                dialog.dismiss()
                mViewModel.ftpTest()
            },
            actionNegative = { dialog, _ ->
                dialog.dismiss()
            }).show()
    }
}