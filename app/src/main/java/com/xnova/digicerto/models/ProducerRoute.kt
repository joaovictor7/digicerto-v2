package com.xnova.digicerto.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "ProducerRoute",
    primaryKeys = ["ProducerCode", "RouteCode"]
)
data class ProducerRoute(
    @ColumnInfo(name = "ProducerCode") val producerCode: Int,
    @ColumnInfo(name = "RouteCode") val routeCode: Int,
    @ColumnInfo(name = "Sequence") val sequence: Int
)