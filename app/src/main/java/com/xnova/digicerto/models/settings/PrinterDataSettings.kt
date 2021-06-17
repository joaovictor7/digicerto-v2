package com.xnova.digicerto.models.settings

import androidx.room.ColumnInfo

data class PrinterDataSettings(
    @ColumnInfo(name = "PrintScale") val printScale: Boolean,
    @ColumnInfo(name = "PrintCompartments") val printCompartments: Boolean,
    @ColumnInfo(name = "PrintSample") val printSample: Boolean,
    @ColumnInfo(name = "ProntTemperature") val printTemperature: Boolean,
    @ColumnInfo(name = "PrintOccurrence") val printOccurrence: Boolean,
    @ColumnInfo(name = "PrintAlizarol") val printAlizarol: Boolean
)