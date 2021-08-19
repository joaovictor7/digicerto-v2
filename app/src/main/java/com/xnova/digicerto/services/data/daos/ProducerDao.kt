package com.xnova.digicerto.services.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.xnova.digicerto.models.entities.Producer

@Dao
interface ProducerDao {
    @Query("select * from Producer where Code = :code")
    fun get(code: Int): Producer?

    @Update
    fun update(producer: Producer)

    @Insert
    fun add(producer: Producer)

    @Query("update Producer set Active = 0")
    fun inactiveAll()
}