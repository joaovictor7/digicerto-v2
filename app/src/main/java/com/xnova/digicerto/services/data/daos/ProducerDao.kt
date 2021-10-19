package com.xnova.digicerto.services.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.xnova.digicerto.models.entities.Producer

@Dao
interface ProducerDao {
    @Query("select * from Producer where Code = :code and FarmCode = :farmCode")
    fun get(code: Int, farmCode: Int): Producer?

    @Update
    fun update(producer: Producer)

    @Insert
    fun add(producer: Producer)

    @Query("update Producer set Active = 0")
    fun inactiveAll()

    @Query("select count(Code) from Producer where Active = 1")
    fun getTotalActive(): Int

    @Query("select * from Producer where Active = 1")
    fun getAllActive(): List<Producer>
}