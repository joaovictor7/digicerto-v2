package com.xnova.digicerto.ui.settings.ws

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xnova.digicerto.R
import com.xnova.digicerto.models.Alert
import com.xnova.digicerto.services.enums.AlertType
import com.xnova.digicerto.services.repositories.local.entities.SettingsRepository
import com.xnova.digicerto.services.util.NetworkUtil

class WSSettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val mApplication = application
    private val mSettingsRepository = SettingsRepository(application)
    private var mSettings = mSettingsRepository.get()

    var wsSettings
        get() = mSettings.wsSettings
        set(value) {
            mSettings.wsSettings = value
        }

    private val mRefreshScreen = MutableLiveData<Boolean>()
    val refreshScreen: LiveData<Boolean> = mRefreshScreen

    private val mShowAlert = MutableLiveData<Alert>()
    val showAlert: LiveData<Alert> = mShowAlert

    private val mShowProgressBar = MutableLiveData<Boolean>()
    val showProgressBar: LiveData<Boolean> = mShowProgressBar

    fun save() {
        mSettingsRepository.update(mSettings)
        refreshEntities()
    }

    fun testWS() {
        if (!NetworkUtil.isOnline(mApplication)) {
            mShowAlert.value =
                Alert(AlertType.NetworkOff, R.string.text_off_line, R.string.msg_network_connect)
            return
        }

        val host = mSettings.wsSettings!!.host!!
        val port = mSettings.wsSettings!!.port!!
        val username = mSettings.wsSettings!!.username!!
        val password = mSettings.wsSettings!!.getPasswordDecrypted()

        mShowAlert.value =
            Alert(AlertType.Error, R.string.text_failure, R.string.msg_ftp_not_working)
        //showProgressBar(true)
    }

    private fun showProgressBar(show: Boolean) {
        mShowProgressBar.value = show
    }

    private fun refreshEntities() {
        mSettings = mSettingsRepository.get()
        mRefreshScreen.value = true
    }
}