package com.xnova.digicerto.models.entities.settings

import androidx.room.ColumnInfo
import com.xnova.digicerto.services.enums.settings.travel.*

data class TravelSettings(
    @ColumnInfo(name = "Type") val type: TravelType,
    @ColumnInfo(name = "ProducerCodeReading") val producerCodeReading: Boolean,
    @ColumnInfo(name = "WildcardProducer") val wildcardProducer: Boolean,
    @ColumnInfo(name = "Transhipment") val transhipment: Boolean,
    @ColumnInfo(name = "TravelCodeReading") val travelCodeReading: TravelCodeReading,
    @ColumnInfo(name = "PlatformMeasure") val platformMeasure: PlatformMeasure,
    @ColumnInfo(name = "MilkDensity") val milkDensity: MilkDensity,
    @ColumnInfo(name = "AwaitDischarge") val awaitDischarge: Boolean,
    @ColumnInfo(name = "OdometerRegister") val odometerRegister: OdometerRegister
)