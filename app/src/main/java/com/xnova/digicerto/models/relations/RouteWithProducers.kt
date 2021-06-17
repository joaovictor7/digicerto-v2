package com.xnova.digicerto.models.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.xnova.digicerto.models.Producer
import com.xnova.digicerto.models.ProducerRoute
import com.xnova.digicerto.models.Route

data class RouteWithProducers(
    @Embedded val route: Route,
    @Relation(
        parentColumn = "code",
        entityColumn = "routeCode",
        associateBy = Junction(ProducerRoute::class)
    )
    val producers: List<Producer>
)