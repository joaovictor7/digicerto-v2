package com.xnova.digicerto.models.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.xnova.digicerto.models.entities.VehicleCompartment
import com.xnova.digicerto.models.entities.Vehicle

data class VehicleWithCompartments(
    @Embedded val vehicle: Vehicle,
    @Relation(
        parentColumn = "Code",
        entityColumn = "VehicleCode"
    )
    val compartments: List<VehicleCompartment>
)