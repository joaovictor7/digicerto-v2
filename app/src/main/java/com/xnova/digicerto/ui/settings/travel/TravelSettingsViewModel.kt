package com.xnova.digicerto.ui.settings.travel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xnova.digicerto.R
import com.xnova.digicerto.services.repositories.local.entities.SettingsRepository

class TravelSettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val mSettingsRepository = SettingsRepository(application)
    private var mSettings = mSettingsRepository.get()

    var travelSettings
        get() = mSettings.travelSettings
        set(value) {
            mSettings.travelSettings = value
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