package com.xnova.digicerto.models.daos

import androidx.room.Dao
import androidx.room.Insert
import com.xnova.digicerto.models.Driver

@Dao
interface DriverDao {
    @Insert
    fun add(driver: Driver)
}