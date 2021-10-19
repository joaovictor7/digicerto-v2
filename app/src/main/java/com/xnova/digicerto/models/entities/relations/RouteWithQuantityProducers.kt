package com.xnova.digicerto.models.entities.relations

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.xnova.digicerto.models.entities.Route

data class RouteWithQuantityProducers(
    @Embedded val route: Route,
    @ColumnInfo(name = "QuantityProducers") val quantityProducers: Int
)