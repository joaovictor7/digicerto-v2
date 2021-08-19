package com.xnova.digicerto.services.repositories.local

import android.content.Context
import com.xnova.digicerto.models.entities.Vehicle
import com.xnova.digicerto.models.entities.VehicleCompartment
import com.xnova.digicerto.services.data.DatabaseService

class VehicleRepository(context: Context) {

    private val mVehicleDao = DatabaseService.getDatabase(context).vehicleDao()

    fun addOrUpdate(vehicle: Vehicle) {
        val v = get(vehicle.code)
        if (v == null)
            mVehicleDao.add(vehicle) else mVehicleDao.update(v)
    }

    fun addOrUpdate(vehicleCompartment: VehicleCompartment) {
        val vc = getCompartment(vehicleCompartment.vehicleCode, vehicleCompartment.compartment)
        if (vc == null)
            mVehicleDao.addCompartment(vehicleCompartment) else mVehicleDao.updateCompartment(
            vehicleCompartment
        )
    }

    fun get(id: Int): Vehicle? {
        return mVehicleDao.get(id)
    }

    fun inactiveAll() {
        mVehicleDao.inactiveAll()
    }

    private fun getCompartment(vehicleCode: Int, compartment: Int): VehicleCompartment? {
        return mVehicleDao.getCompartment(vehicleCode, compartment)
    }
}