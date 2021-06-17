package com.xnova.digicerto.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Vehicle")
data class Vehicle(
    @PrimaryKey @ColumnInfo(name = "Code") val code: Int,
    @ColumnInfo(name = "Plate") val plate: String,
    @ColumnInfo(name = "Active") val active: Boolean
)