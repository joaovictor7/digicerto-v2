package com.xnova.digicerto.services.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.xnova.digicerto.models.entities.Driver

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

    @Query("select count(Code) from Driver where Active = 1")
    fun getTotalActives(): Int

    @Query("select * from Driver where Active = 1")
    fun getAllActive(): List<Driver>
}