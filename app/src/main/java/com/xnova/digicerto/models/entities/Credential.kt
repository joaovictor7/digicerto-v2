package com.xnova.digicerto.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.apache.commons.codec.binary.Base64

@Entity(tableName = "Credential")
data class Credential(
    @ColumnInfo(name = "Username") val username: String
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    var id: Int = 0

    @ColumnInfo(name = "Password")
    var password: String = ""

    fun setPasswordDecrypted(password: String) {
        this.password = Base64.encodeBase64String(password.toByteArray()).toString()
    }

    fun authenticate(username: String, password: String): Boolean {
        return this.username.equals(username, true) && getPasswordDecrypted() == password
    }

    private fun getPasswordDecrypted(): String {
        return Base64.decodeBase64(password).decodeToString()
    }
}