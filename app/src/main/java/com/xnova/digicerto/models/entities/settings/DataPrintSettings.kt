package com.xnova.digicerto.models.entities.settings

import androidx.room.ColumnInfo

data class DataPrintSettings(
    @ColumnInfo(name = "PrintScale") val printScale: Boolean,
    @ColumnInfo(name = "PrintCompartments") val printCompartments: Boolean,
    @ColumnInfo(name = "PrintSample") val printSample: Boolean,
    @ColumnInfo(name = "PrintTemperature") val printTemperature: Boolean,
    @ColumnInfo(name = "PrintOccurrence") val printOccurrence: Boolean,
    @ColumnInfo(name = "PrintAlizarol") val printAlizarol: Boolean
)