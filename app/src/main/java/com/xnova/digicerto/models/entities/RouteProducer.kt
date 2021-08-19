package com.xnova.digicerto.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "RouteProducer",
    primaryKeys = ["RouteCode", "ProducerCode", "ProducerFarmCode"]
)
data class RouteProducer(
    @ColumnInfo(name = "RouteCode") val routeCode: Int,
    @ColumnInfo(name = "ProducerCode") val producerCode: Int,
    @ColumnInfo(name = "ProducerFarmCode") val producerFarmCode: Int,
    @ColumnInfo(name = "Sequence") val sequence: Int
)