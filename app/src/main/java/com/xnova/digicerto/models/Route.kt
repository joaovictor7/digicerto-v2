package com.xnova.digicerto.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Route")
data class Route(
    @PrimaryKey @ColumnInfo(name = "Code") val code: Int,
    @ColumnInfo(name = "Active") val active: Boolean = true,
    @ColumnInfo(name = "Name") val name: String
)