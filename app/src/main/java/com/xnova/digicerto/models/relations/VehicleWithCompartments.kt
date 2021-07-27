package com.xnova.digicerto.models.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.xnova.digicerto.models.VehicleCompartment
import com.xnova.digicerto.models.Vehicle

data class VehicleWithCompartments(
    @Embedded val vehicle: Vehicle,
    @Relation(
        parentColumn = "Code",
        entityColumn = "VehicleCode"
    )
    val compartments: List<VehicleCompartment>
)