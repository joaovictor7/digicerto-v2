package com.xnova.digicerto.models.settings

import androidx.room.ColumnInfo

data class FTPSettings(
    @ColumnInfo(name = "Host") val host: String?,
    @ColumnInfo(name = "Port") val port: Int?,
    @ColumnInfo(name = "Username") val username: String?,
    @ColumnInfo(name = "Password") val password: String?,
    @ColumnInfo(name = "Folder") val folder: String?
)