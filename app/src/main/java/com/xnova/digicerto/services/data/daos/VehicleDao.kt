package com.xnova.digicerto.services.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.xnova.digicerto.models.Vehicle
import com.xnova.digicerto.models.VehicleCompartment

@Dao
interface VehicleDao {

    @Query("select * from Vehicle where Code = :code")
    fun get(code: Int): Vehicle?

    @Update
    fun update(vehicle: Vehicle)

    @Query("update Vehicle set Active = 0")
    fun inactiveAll()

    @Insert
    fun add(vehicle: Vehicle)

    @Query("select * from VehicleCompartment where VehicleCode = :vehicleCode and Compartment = :compartment")
    fun getCompartment(vehicleCode: Int, compartment: Int): VehicleCompartment?

    @Update
    fun updateCompartment(vehicleCompartment: VehicleCompartment)

    @Insert
    fun addCompartment(vehicleCompartment: VehicleCompartment)
}