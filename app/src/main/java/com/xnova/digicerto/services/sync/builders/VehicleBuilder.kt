package com.xnova.digicerto.services.sync.builders

import android.content.Context
import com.xnova.digicerto.models.Vehicle
import com.xnova.digicerto.models.VehicleCompartment
import com.xnova.digicerto.models.relations.VehicleWithCompartments
import com.xnova.digicerto.services.constants.SyncConstants
import com.xnova.digicerto.services.util.NumberUtil

class VehicleBuilder(context: Context) : Build(context, "VEICULO") {

    override fun validate(line: List<String>): Boolean {
        val compartments = getValidCompartmentsVehicle(line)

        return if (
            line.count() >= SyncConstants.FTP.KEYS.VEHICLE.MINIMUM_DATA &&
            NumberUtil.isInt(line[SyncConstants.FTP.KEYS.VEHICLE.CODE]) &&
            line[SyncConstants.FTP.KEYS.VEHICLE.PLATE].isNotBlank() &&
            compartments.isNotEmpty()
        ) {
            true
        } else {
            formatException()
        }
    }

    override fun build(line: List<String>): VehicleWithCompartments {
        val code = line[SyncConstants.FTP.KEYS.VEHICLE.CODE].toInt()
        val plate = line[SyncConstants.FTP.KEYS.VEHICLE.PLATE].replace("-", "")

        return VehicleWithCompartments(
            vehicle = Vehicle(
                code = code,
                plate = plate.trim().uppercase()
            ),
            compartments = compartmentsVehicleBuild(code, line)
        )
    }

    private fun getValidCompartmentsVehicle(line: List<String>): List<String> =
        line.subList(SyncConstants.FTP.KEYS.VEHICLE.FIRST_COMPARTMENT, line.size)
            .filter { it.toIntOrNull() != null && it.toInt() > 0 }

    private fun compartmentsVehicleBuild(
        vehicleCode: Int,
        line: List<String>
    ): List<VehicleCompartment> {
        val stringCompartments = getValidCompartmentsVehicle(line)
        var compartment = 0

        return stringCompartments.map {
            compartment++
            VehicleCompartment(vehicleCode, compartment, it.toInt())
        }
    }
}