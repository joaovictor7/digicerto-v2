package com.xnova.digicerto.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Occurrence")
data class Occurrence(
    @PrimaryKey @ColumnInfo(name = "Code") val code: Int,
    @ColumnInfo(name = "Active") val active: Boolean,
    @ColumnInfo(name = "Description") val description: String
)