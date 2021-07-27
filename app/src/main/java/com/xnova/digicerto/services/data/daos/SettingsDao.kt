package com.xnova.digicerto.services.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.xnova.digicerto.models.settings.Settings

@Dao
interface SettingsDao {
    @Query("select * from Settings")
    fun get(): Settings

    @Update
    fun update(settings: Settings)
}