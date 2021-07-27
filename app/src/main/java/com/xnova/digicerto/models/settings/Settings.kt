package com.xnova.digicerto.models.settings

import androidx.room.*
import com.xnova.digicerto.services.enums.OperationType
import com.xnova.digicerto.services.repositories.local.Converters
import java.util.*

@Entity(tableName = "Settings")
data class Settings(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "Id") val id: Int,
    @ColumnInfo(name = "OperationType") var operationType: OperationType?,
    @ColumnInfo(name = "LatestSync") var latestSync: Calendar?,
    @Embedded(prefix = "Travel_") val travelSettings: TravelSettings,
    @Embedded(prefix = "FTP_") var ftpSettings: FTPSettings?,
    @Embedded(prefix = "WS_") val wsSettings: WSSettings?,
    @Embedded(prefix = "Printer_") val printerSettings: PrinterSettings
) {
    @Ignore
    val operationTypeAvailable: Boolean = operationType != null
    @Ignore
    val ftpOperationAvailable: Boolean = ftpSettings != null
    @Ignore
    val wsOperationAvailable: Boolean = wsSettings != null
}