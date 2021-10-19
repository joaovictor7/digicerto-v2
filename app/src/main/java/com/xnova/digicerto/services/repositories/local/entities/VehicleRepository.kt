package com.xnova.digicerto.services.repositories.local.entities

import android.content.Context
import com.xnova.digicerto.models.entities.Vehicle
import com.xnova.digicerto.models.entities.VehicleCompartment
import com.xnova.digicerto.models.entities.relations.VehicleWithCompartments
import com.xnova.digicerto.services.data.DatabaseService

class VehicleRepository(context: Context) {

    private val mVehicleDao = DatabaseService.getDatabase(context).vehicleDao()

    fun addOrUpdate(vehicle: Vehicle) {
        val v = get(vehicle.code)
        if (v == null) {
            mVehicleDao.add(vehicle)
        } else {
            mVehicleDao.update(vehicle)
        }
    }

    fun addOrUpdate(vehicleCompartment: VehicleCompartment) {
        val vc = getCompartment(vehicleCompartment.vehicleCode, vehicleCompartment.compartment)
        if (vc == null) {
            mVehicleDao.addCompartment(vehicleCompartment)
        } else {
            mVehicleDao.updateCompartment(vehicleCompartment)
        }
    }

    fun get(code: Int): Vehicle? {
        return mVehicleDao.get(code)
    }

    fun inactiveAll() {
        mVehicleDao.inactiveAll()
    }

    fun getTotalActives(): Int {
        return mVehicleDao.getTotalActives()
    }

    private fun getCompartment(vehicleCode: Int, compartment: Int): VehicleCompartment? {
        return mVehicleDao.getCompartment(vehicleCode, compartment)
    }

    fun getAllActive(): List<VehicleWithCompartments> {
        return mVehicleDao.getAllActive()
    }
}