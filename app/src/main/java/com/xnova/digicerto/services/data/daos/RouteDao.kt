package com.xnova.digicerto.services.data.daos

import androidx.room.*
import com.xnova.digicerto.models.entities.Route
import com.xnova.digicerto.models.entities.RouteProducer
import com.xnova.digicerto.models.entities.relations.RouteWithQuantityProducers

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

    @Query("select count(Code) from Route where Active = 1")
    fun getTotalActives(): Int

    @Transaction
    @Query(
        "select " +
            "   r.*, " +
            "   count(rp.RouteCode) as 'QuantityProducers' " +
            "from Route as r " +
            "left join RouteProducer as rp " +
            "   on r.Code = rp.RouteCode " +
            "left join Producer as p " +
            "   on rp.ProducerCode = p.Code and rp.ProducerFarmCode = p.FarmCode " +
            "where r.Active = 1 and " +
            "      p.Active = 1 " +
            "group by r.Code," +
            "         r.Active, " +
            "         r.Name"
    )
    fun getAllActive(): List<RouteWithQuantityProducers>
}