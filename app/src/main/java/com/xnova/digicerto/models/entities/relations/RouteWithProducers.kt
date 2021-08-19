package com.xnova.digicerto.models.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.xnova.digicerto.models.entities.Producer
import com.xnova.digicerto.models.entities.RouteProducer
import com.xnova.digicerto.models.entities.Route

data class RouteWithProducers(
    @Embedded val route: Route,
    @Relation(
        parentColumn = "code",
        entityColumn = "routeCode",
        associateBy = Junction(RouteProducer::class)
    )
    val producers: List<Producer>
)