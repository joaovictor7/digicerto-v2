package com.xnova.digicerto.models.settings

import androidx.room.ColumnInfo
import org.apache.commons.codec.binary.Base64

data class AuthenticationSettings(
    @ColumnInfo(name = "Username") val username: String?
) {

    @ColumnInfo(name = "Password")
    var password: String? = null

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