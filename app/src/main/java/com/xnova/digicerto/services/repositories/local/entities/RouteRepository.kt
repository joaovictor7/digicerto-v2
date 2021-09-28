package com.xnova.digicerto.services.repositories.local.entities

import android.content.Context
import com.xnova.digicerto.models.entities.Route
import com.xnova.digicerto.models.entities.RouteProducer
import com.xnova.digicerto.services.data.DatabaseService

class RouteRepository(context: Context) {

    private val mRouteDao = DatabaseService.getDatabase(context).routeDao()

    fun addOrUpdate(route: Route) {
        val r = get(route.code)
        if (r == null)
            mRouteDao.add(route) else mRouteDao.update(route)
    }

    fun addOrUpdate(routeProducer: RouteProducer) {
        val rp = getRouteProducer(
            routeProducer.routeCode,
            routeProducer.producerCode,
            routeProducer.producerFarmCode
        )
        if (rp == null)
            mRouteDao.addRouteProducer(routeProducer) else mRouteDao.updateRouteProducer(
            routeProducer
        )
    }

    fun get(id: Int): Route? {
        return mRouteDao.get(id)
    }

    fun inactiveAll() {
        mRouteDao.inactiveAll()
    }

    private fun getRouteProducer(
        routeCode: Int,
        producerCode: Int,
        producerFarmCode: Int
    ): RouteProducer? {
        return mRouteDao.getRouteProducer(routeCode, producerCode, producerFarmCode)
    }
}