package com.xnova.digicerto.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "VehicleCompartment",
    primaryKeys = ["VehicleCode", "Compartment"]
)
data class VehicleCompartment(
    @ColumnInfo(name = "VehicleCode") val vehicleCode: Int,
    @ColumnInfo(name = "Compartment") val compartment: Int,
    @ColumnInfo(name = "Capacity") val capacity: Int
)