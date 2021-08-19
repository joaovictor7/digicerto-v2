package com.xnova.digicerto.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xnova.digicerto.models.MenuSettings
import com.xnova.digicerto.services.repositories.local.SettingsRepository

class ApplicationSettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val mApplication = application
    private val mSettingsRepository = SettingsRepository(application)
    val settings = mSettingsRepository.get()

    private val mSettingsListPair = MutableLiveData<List<MenuSettings>>()
    val settingsListPair: LiveData<List<MenuSettings>> = mSettingsListPair

    fun saveSettings() {
        mSettingsRepository.update(settings)
    }
}