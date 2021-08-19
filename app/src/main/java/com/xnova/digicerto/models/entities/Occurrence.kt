package com.xnova.digicerto.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.xnova.digicerto.services.enums.OccurrenceType
import com.xnova.digicerto.services.enums.TankType

@Entity(tableName = "Occurrence")
data class Occurrence(
    @PrimaryKey @ColumnInfo(name = "Code") val code: Int,
    @ColumnInfo(name = "Active") val active: Boolean = true,
    @ColumnInfo(name = "Type") val type: OccurrenceType,
    @ColumnInfo(name = "Description") val description: String
)