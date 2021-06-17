package com.xnova.digicerto.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "VehicleCompartments",
    primaryKeys = ["VehicleCode", "Compartment"]
)
data class VehicleCompartments(
    @ColumnInfo(name = "VehicleCode") val vehicleCode: Int,
    @ColumnInfo(name = "Compartment") val compartment: Int,
    @ColumnInfo(name = "Capacity") val capacity: Int
)