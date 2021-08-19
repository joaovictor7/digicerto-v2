package com.xnova.digicerto.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import com.xnova.digicerto.R
import com.xnova.digicerto.databinding.ActivityApplicationSettingsBinding
import com.xnova.digicerto.services.enums.OperationType

class ApplicationSettingsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityApplicationSettingsBinding
    private lateinit var mViewModel: ApplicationSettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProvider(this).get(ApplicationSettingsViewModel::class.java)
        mBinding = ActivityApplicationSettingsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setActionBar()
        listeners()
        setComponents()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.text_application)
    }

    private fun listeners() {
        radioFTPListener()
        radioWSListener()
    }

    private fun radioFTPListener() {
        mBinding.radioFTP.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mViewModel.settings.operationType = OperationType.FTP
                mViewModel.saveSettings()
            }
        }
    }

    private fun radioWSListener() {
        mBinding.radioWS.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mViewModel.settings.operationType = OperationType.WebService
                mViewModel.saveSettings()
            }
        }
    }

    private fun setComponents() {
        if (mViewModel.settings.operationType == OperationType.FTP) {
            mBinding.radioFTP.isChecked = true
        } else {
            mBinding.radioWS.isChecked = true
        }
    }
}