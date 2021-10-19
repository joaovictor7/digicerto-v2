package com.xnova.digicerto.services.data.daos

import androidx.room.*
import com.xnova.digicerto.models.entities.Vehicle
import com.xnova.digicerto.models.entities.VehicleCompartment
import com.xnova.digicerto.models.entities.relations.VehicleWithCompartments

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

    @Query("select count(Code) from Vehicle where Active = 1")
    fun getTotalActives(): Int

    @Transaction
    @Query("select * from Vehicle where Active = 1")
    fun getAllActive(): List<VehicleWithCompartments>
}