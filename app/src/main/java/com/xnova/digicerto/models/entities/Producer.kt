package com.xnova.digicerto.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.xnova.digicerto.services.enums.producer.TankType

@Entity(
    tableName = "Producer",
    primaryKeys = ["Code", "FarmCode"]
)
class Producer(
    @ColumnInfo(name = "Code") val code: Int,
    @ColumnInfo(name = "FarmCode") val farmCode: Int,
    @ColumnInfo(name = "Active") val active: Boolean = true,
    @ColumnInfo(name = "Name") val name: String?,
    @ColumnInfo(name = "FarmName") val farmName: String?,
    @ColumnInfo(name = "AvgVolume") val avgVolume: Double?,
    @ColumnInfo(name = "TankType") val tankType: TankType,
    @ColumnInfo(name = "TankCode") val tankCode: Int?,
    @ColumnInfo(name = "Note") val note: String?
)