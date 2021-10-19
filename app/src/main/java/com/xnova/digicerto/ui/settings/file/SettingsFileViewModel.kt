package com.xnova.digicerto.ui.settings.file

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xnova.digicerto.R
import com.xnova.digicerto.services.repositories.local.entities.SettingsRepository

class SettingsFileViewModel(application: Application) : AndroidViewModel(application) {

    private val mSettingsRepository = SettingsRepository(application)
    private var mSettings = mSettingsRepository.get()

    var fileSettings
        get() = mSettings.fileSettings
        set(value) {
            mSettings.fileSettings = value
        }

    private val mRefreshScreen = MutableLiveData<Boolean>()
    val refreshScreen: LiveData<Boolean> = mRefreshScreen

    private val mSaveMsg = MutableLiveData<Int>()
    val saveMsg: LiveData<Int> = mSaveMsg

    fun save() {
        mSettingsRepository.update(mSettings)
        refreshEntities()
        mSaveMsg.value = R.string.text_settings_saved
    }

    private fun refreshEntities() {
        mSettings = mSettingsRepository.get()
        mRefreshScreen.value = true
    }
}