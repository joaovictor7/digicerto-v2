package com.xnova.digicerto.models.entities.settings

import androidx.room.ColumnInfo
import com.xnova.digicerto.services.enums.settings.collect.CollectiveTankCollection
import com.xnova.digicerto.services.enums.settings.collect.RegisterSample

data class CollectSettings(
    @ColumnInfo(name = "CollectiveTankCollection") val collectiveTankCollection: CollectiveTankCollection,
    @ColumnInfo(name = "RegisterSample") val registerSample: RegisterSample,
    @ColumnInfo(name = "AcidMilk") val acidMilk: Boolean,
    @ColumnInfo(name = "TemperaturePicture") val temperaturePicture: Boolean,
    @ColumnInfo(name = "ProductionTolerancePercentage") val productionTolerancePercentage: Double?
)