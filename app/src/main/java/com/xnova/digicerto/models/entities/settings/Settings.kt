package com.xnova.digicerto.models.entities.settings

import androidx.room.*
import com.xnova.digicerto.services.enums.settings.OperationType
import java.util.*

@Entity(tableName = "Settings")
data class Settings(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "Id") val id: Int,
    @ColumnInfo(name = "OperationType") var operationType: OperationType?,
    @ColumnInfo(name = "Email") var email: String?,
    @ColumnInfo(name = "LatestSync") var latestSync: Calendar?,
    @Embedded(prefix = "File_") var fileSettings: FileSettings,
    @Embedded(prefix = "Travel_") var travelSettings: TravelSettings,
    @Embedded(prefix = "Collect_") var collectSettings: CollectSettings,
    @Embedded(prefix = "FTP_") var ftpSettings: FTPSettings?,
    @Embedded(prefix = "WS_") var wsSettings: WSSettings?,
    @Embedded(prefix = "Printer_") var printerSettings: PrinterSettings
) {
    @Ignore
    val operationTypeAvailable: Boolean = operationType != null

    @Ignore
    val ftpOperationAvailable: Boolean = ftpSettings != null

    @Ignore
    val wsOperationAvailable: Boolean = wsSettings != null

    @Ignore
    val necessaryChooseTypeOperation: Boolean = operationType == null

    fun updateLatestSync() {
        latestSync = Calendar.getInstance()
    }
}