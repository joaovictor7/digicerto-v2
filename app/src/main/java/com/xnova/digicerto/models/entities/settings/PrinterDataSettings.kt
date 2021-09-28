package com.xnova.digicerto.models.entities.settings

import androidx.room.ColumnInfo

data class PrinterDataSettings(
    @ColumnInfo(name = "Printer") val printer: String?,
    @ColumnInfo(name = "MacAddress") val macAddress: String?
)