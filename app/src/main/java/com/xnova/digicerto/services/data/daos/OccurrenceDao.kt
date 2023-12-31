package com.xnova.digicerto.services.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.xnova.digicerto.models.entities.Occurrence

@Dao
interface OccurrenceDao {
    @Insert
    fun add(occurrence: Occurrence)

    @Query("select * from Occurrence where Code = :code")
    fun get(code: Int): Occurrence?

    @Update
    fun update(occurrence: Occurrence)

    @Query("update Occurrence set Active = 0")
    fun inactiveAll()

    @Query("select count(Code) from Occurrence where Active = 1")
    fun getTotalActive(): Int

    @Query("select * from Occurrence where Active = 1")
    fun getAllActive(): List<Occurrence>
}