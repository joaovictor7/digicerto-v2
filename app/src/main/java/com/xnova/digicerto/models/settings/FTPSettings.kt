package com.xnova.digicerto.models.settings

import androidx.room.ColumnInfo
import androidx.room.Ignore
import org.apache.commons.codec.binary.Base64

data class FTPSettings(
    @ColumnInfo(name = "Host") val host: String?,
    @ColumnInfo(name = "Port") val port: Int?,
    @ColumnInfo(name = "Username") val username: String?,
    @ColumnInfo(name = "Folder") val folder: String?,
) {

    @ColumnInfo(name = "Password")
    var password: String? = null

    fun getPasswordDecrypted(): String {
        return Base64.decodeBase64(password).decodeToString()
    }

    fun setPasswordDecrypted(password: String) {
        this.password = Base64.encodeBase64String(password.toByteArray()).toString()
    }
}