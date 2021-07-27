package com.xnova.digicerto.models.settings

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.xnova.digicerto.services.enums.PrintType
import com.xnova.digicerto.services.enums.SecondVia

data class PrinterSettings(
    @ColumnInfo(name = "UsePrinter") val usePrinter: Boolean,
    @ColumnInfo(name = "SecondVia") val secondVia: SecondVia,
    @ColumnInfo(name = "CancelledCollections") val canceledCollections: Boolean,
    @ColumnInfo(name = "PrintType") val printType: PrintType,
    @ColumnInfo(name = "Printer") val printer: String?,
    @Embedded val data: PrinterDataSettings
)