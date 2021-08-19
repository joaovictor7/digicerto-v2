package com.xnova.digicerto.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Driver")
data class Driver(
    @PrimaryKey @ColumnInfo(name = "Code") val code: Int,
    @ColumnInfo(name = "Name") val name: String,
    @ColumnInfo(name = "Active") val active: Boolean = true
)