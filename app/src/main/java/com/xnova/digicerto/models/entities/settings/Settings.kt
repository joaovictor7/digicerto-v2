package com.xnova.digicerto.models.entities.settings

import androidx.room.*
import com.xnova.digicerto.services.enums.OperationType
import java.util.*

@Entity(tableName = "Settings")
data class Settings(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "Id") val id: Int,
    @ColumnInfo(name = "OperationType") var operationType: OperationType?,
    @ColumnInfo(name = "LatestSync") var latestSync: Calendar?,
    @Embedded(prefix = "Authentication_") var authentication: AuthenticationSettings?,
    @Embedded(prefix = "Travel_") val travelSettings: TravelSettings,
    @Embedded(prefix = "Collect_") val collectSettings: CollectSettings,
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
    @Ignore
    val authenticationAvailable: Boolean = authentication != null
    @Ignore
    val necessaryChooseTypeOperation: Boolean = operationType == null

    fun updateLatestSync() {
        latestSync = Calendar.getInstance()
    }
}