package com.xnova.digicerto.models.settings

import androidx.room.ColumnInfo
import com.xnova.digicerto.services.enums.*

data class TravelSettings(
    @ColumnInfo(name = "Type") val type: RouteType,
    @ColumnInfo(name = "ReadBarcode") val readBarcode: Boolean,
    @ColumnInfo(name = "WildcardProducer") val wildcardProducer: Boolean,
    @ColumnInfo(name = "Transhipment") val transhipment: Boolean,
    @ColumnInfo(name = "CollectiveTankProduction") val collectiveTankProduction: CollectiveTankProduction,
    @ColumnInfo(name = "ReadSample") val readSample: ReadSampleType,
    @ColumnInfo(name = "AcidMilk") val acidMilk: Boolean,
    @ColumnInfo(name = "TypeTravelCodeReading") val typeTravelCodeReading: TypeTravelCodeReading,
    @ColumnInfo(name = "PlatformMeasure") val platformMeasure: PlatformMeasure,
    @ColumnInfo(name = "MilkDensityType") val milkDensityType: MilkDensityType,
    @ColumnInfo(name = "AwaitDischarge") val awaitDischarge: Boolean,
    @ColumnInfo(name = "TemperaturePicture") val temperaturePicture: Boolean,
    @ColumnInfo(name = "Odometer") val odometer: Boolean,
    @ColumnInfo(name = "ProductionTolerancePercentage") val productionTolerancePercentage: Double?,
    @ColumnInfo(name = "Email") val email: String?
)