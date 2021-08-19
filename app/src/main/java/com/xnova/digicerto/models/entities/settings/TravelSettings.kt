package com.xnova.digicerto.models.entities.settings

import androidx.room.ColumnInfo
import com.xnova.digicerto.services.enums.*

data class TravelSettings(
    @ColumnInfo(name = "Type") val type: TravelType,
    @ColumnInfo(name = "ReadBarcode") val readBarcode: Boolean,
    @ColumnInfo(name = "WildcardProducer") val wildcardProducer: Boolean,
    @ColumnInfo(name = "Transhipment") val transhipment: Boolean,
    @ColumnInfo(name = "TypeTravelCodeReading") val typeTravelCodeReading: TypeTravelCodeReading,
    @ColumnInfo(name = "PlatformMeasure") val platformMeasure: PlatformMeasure,
    @ColumnInfo(name = "MilkDensityType") val milkDensityType: MilkDensityType,
    @ColumnInfo(name = "AwaitDischarge") val awaitDischarge: Boolean,
    @ColumnInfo(name = "Odometer") val odometer: Boolean,
    @ColumnInfo(name = "Email") val email: String?
)