package com.xnova.digicerto.services.repositories.local.entities

import android.content.Context
import com.xnova.digicerto.models.entities.settings.Settings
import com.xnova.digicerto.services.data.DatabaseService

class SettingsRepository(context: Context) {

    private val mSettingsDao = DatabaseService.getDatabase(context).settingsDao()

    fun get(): Settings {
        return mSettingsDao.get()
    }

    fun update(settings: Settings) {
        mSettingsDao.update(settings)
    }
}