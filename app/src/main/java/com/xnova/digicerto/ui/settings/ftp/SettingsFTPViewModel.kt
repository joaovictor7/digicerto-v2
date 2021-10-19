package com.xnova.digicerto.ui.settings.ftp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xnova.digicerto.R
import com.xnova.digicerto.models.Alert
import com.xnova.digicerto.services.enums.AlertType
import com.xnova.digicerto.services.repositories.local.entities.SettingsRepository
import com.xnova.digicerto.services.repositories.remote.FTPRepository
import com.xnova.digicerto.services.util.NetworkUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SettingsFTPViewModel(application: Application) : AndroidViewModel(application) {

    private val mApplication = application
    private val mSettingsRepository = SettingsRepository(application)
    private var mSettings = mSettingsRepository.get()

    var ftpSettings
        get() = mSettings.ftpSettings
        set(value) {
            mSettings.ftpSettings = value
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

    private fun refreshEntities() {
        mSettings = mSettingsRepository.get()
        mRefreshScreen.value = true
    }

    fun ftpTest() {
        if (!ftpValidations()) {
            return
        }

        val host = mSettings.ftpSettings!!.host!!
        val port = mSettings.ftpSettings!!.port!!
        val username = mSettings.ftpSettings!!.username!!
        val password = mSettings.ftpSettings!!.getPasswordDecrypted()

        showProgressBar(true)
        val ftpRepository = FTPRepository(mApplication, host, port)
        ftpRepository.connect(username, password)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {
                showProgressBar(false)
                mShowAlert.value =
                    Alert(AlertType.Error, R.string.text_failure, R.string.msg_ftp_not_working)
            }, {
                showProgressBar(false)
                mShowAlert.value =
                    Alert(AlertType.Success, R.string.text_success, R.string.msg_ftp_working)
            })
    }

    private fun ftpValidations(): Boolean {
        if (!NetworkUtil.isOnline(mApplication)) {
            mShowAlert.value =
                Alert(AlertType.NetworkOff, R.string.text_off_line, R.string.msg_network_connect)
            return false
        }

        return true
    }

    private fun showProgressBar(show: Boolean) {
        mShowProgressBar.value = show
    }
}