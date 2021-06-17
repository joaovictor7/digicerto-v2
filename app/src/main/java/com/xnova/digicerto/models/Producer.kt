package com.xnova.digicerto.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Producer")
data class Producer(
    @PrimaryKey @ColumnInfo(name = "Code") val code: Int,
    @ColumnInfo(name = "FarmCode") val farmCode: Int,
    @ColumnInfo(name = "Active") val active: Boolean,
    @ColumnInfo(name = "Name") val name: String,
    @ColumnInfo(name = "FarmName") val farmName: String?,
    @ColumnInfo(name = "AvgVolume") val avgVolume: Double?,
    @ColumnInfo(name = "TankType") val tankType: Int,
    @ColumnInfo(name = "TankCode") val tankCode: Int?,
    @ColumnInfo(name = "Note") val note: String?
)