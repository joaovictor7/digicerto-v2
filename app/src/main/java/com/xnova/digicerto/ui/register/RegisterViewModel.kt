package com.xnova.digicerto.ui.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xnova.digicerto.R
import com.xnova.digicerto.services.repositories.local.entities.SettingsRepository
import org.ocpsoft.prettytime.PrettyTime


class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val mApplication = application
    private val mSettingsRepository = SettingsRepository(application)
    private val mPrettyTime = PrettyTime()

    var settings = mSettingsRepository.get()
        private set

    private val mRefreshScreen = MutableLiveData<Boolean>()
    val refreshScreen: LiveData<Boolean> = mRefreshScreen

    fun sync() {
        mSettingsRepository.update(settings)
        refreshEntities()
    }

    fun refreshEntities() {
        settings = mSettingsRepository.get()
        mRefreshScreen.value = true
    }

    fun getLatestSync(): String {
        return settings.latestSync?.let {
            mPrettyTime.format(settings.latestSync)
        } ?: mApplication.getString(R.string.msg_not_sync)
    }
}