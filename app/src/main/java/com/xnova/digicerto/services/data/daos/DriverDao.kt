package com.xnova.digicerto.services.data.daos

import androidx.room.*
import com.xnova.digicerto.models.Driver

@Dao
interface DriverDao {
    @Insert
    fun add(driver: Driver)

    @Query("select * from Driver where Code = :code")
    fun get(code: Int): Driver?

    @Update
    fun update(driver: Driver)

    @Query("update Driver set Active = 0")
    fun inactiveAll()
}