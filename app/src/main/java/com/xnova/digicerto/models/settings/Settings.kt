package com.xnova.digicerto.models.settings

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.xnova.digicerto.services.enums.OperationType

@Entity(tableName = "Settings")
data class Settings(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "Id") val id: Int,
    @ColumnInfo(name = "OperationType") val operationType: OperationType,
    @Embedded(prefix = "Travel_") val travelSettings: TravelSettings,
    @Embedded(prefix = "FTP_") val ftpSettings: FTPSettings?,
    @Embedded(prefix = "WS_") val wsSettings: WSSettings?,
    @Embedded(prefix = "Printer_") val printerSettings: PrinterSettings
)