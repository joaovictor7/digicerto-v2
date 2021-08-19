package com.xnova.digicerto.services.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.xnova.digicerto.models.entities.RouteProducer
import com.xnova.digicerto.models.entities.Route

@Dao
interface RouteDao {

    @Query("select * from Route where Code = :code")
    fun get(code: Int): Route?

    @Update
    fun update(route: Route)

    @Insert
    fun add(route: Route)

    @Query("update Route set Active = 0")
    fun inactiveAll()

    @Query("select * from RouteProducer where RouteCode = :routeCode and ProducerCode = :producerCode and ProducerFarmCode = :producerFarmCode")
    fun getRouteProducer(routeCode: Int, producerCode: Int, producerFarmCode: Int): RouteProducer?

    @Update
    fun updateRouteProducer(routeProducer: RouteProducer)

    @Insert
    fun addRouteProducer(routeProducer: RouteProducer)
}