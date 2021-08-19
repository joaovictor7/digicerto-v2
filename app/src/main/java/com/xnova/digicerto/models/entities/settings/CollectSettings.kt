package com.xnova.digicerto.models.entities.settings

import androidx.room.ColumnInfo
import com.xnova.digicerto.services.enums.CollectiveTankProduction
import com.xnova.digicerto.services.enums.ReadSampleType

data class CollectSettings(
    @ColumnInfo(name = "CollectiveTankProduction") val collectiveTankProduction: CollectiveTankProduction,
    @ColumnInfo(name = "ReadSample") val readSample: ReadSampleType,
    @ColumnInfo(name = "AcidMilk") val acidMilk: Boolean,
    @ColumnInfo(name = "TemperaturePicture") val temperaturePicture: Boolean,
    @ColumnInfo(name = "ProductionTolerancePercentage") val productionTolerancePercentage: Double?
)