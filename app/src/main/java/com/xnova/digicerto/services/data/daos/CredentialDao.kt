package com.xnova.digicerto.services.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.xnova.digicerto.models.entities.Credential

@Dao
interface CredentialDao {
    @Query("select * from Credential")
    fun get(): Credential?

    @Update
    fun update(credential: Credential)

    @Insert
    fun add(credential: Credential)
}