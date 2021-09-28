package com.xnova.digicerto.models.entities.settings

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.xnova.digicerto.services.enums.settings.printer.LayoutPrinter
import com.xnova.digicerto.services.enums.settings.printer.Dupplicate

data class PrinterSettings(
    @ColumnInfo(name = "UsePrinter") val usePrinter: Boolean,
    @ColumnInfo(name = "Dupplicate") val dupplicate: Dupplicate,
    @ColumnInfo(name = "CollectCancelled") val collectCancelled: Boolean,
    @ColumnInfo(name = "Layout") val layoutPrinter: LayoutPrinter,
    @Embedded val printerData: PrinterDataSettings?,
    @Embedded val dataPrint: DataPrintSettings
)