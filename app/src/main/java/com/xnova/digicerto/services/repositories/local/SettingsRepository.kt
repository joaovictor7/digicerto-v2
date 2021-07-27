package com.xnova.digicerto.services.repositories.local

import android.content.Context
import com.xnova.digicerto.models.settings.Settings
import com.xnova.digicerto.services.data.DatabaseService
import java.util.*

class SettingsRepository(context: Context) {

    private val mSettingsDao = DatabaseService.getDatabase(context).settingsDao()

    fun get(): Settings {
        return mSettingsDao.get()
    }

    fun updateLatestSync() {
        val s = get()
        s.latestSync = Calendar.getInstance()
        mSettingsDao.update(s)
    }
}