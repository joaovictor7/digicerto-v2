package com.xnova.digicerto.services.sync.builders

import android.content.Context
import com.xnova.digicerto.models.entities.Vehicle
import com.xnova.digicerto.models.entities.VehicleCompartment
import com.xnova.digicerto.models.entities.relations.VehicleWithCompartments
import com.xnova.digicerto.services.constants.SyncConstants
import com.xnova.digicerto.services.util.NumberUtil

class VehicleBuilder(context: Context) : Builder(context, REGISTER_TYPE) {

    companion object {
        const val REGISTER_TYPE = "VEICULO"
    }

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

    private fun getValidCompartmentsVehicle(line: List<String>): List<String> {
        return line
            .subList(SyncConstants.FTP.KEYS.VEHICLE.FIRST_COMPARTMENT, line.size)
            .filter { it.toIntOrNull() != null && it.toInt() > 0 }
    }

    private fun compartmentsVehicleBuild(
        vehicleCode: Int,
        line: List<String>
    ): List<VehicleCompartment> {
        val compartments = getValidCompartmentsVehicle(line)
        return if (compartments.count() > 8) {
            compartments.subList(0, 8).mapIndexed { i, s ->
                VehicleCompartment(vehicleCode, i.inc(), s.toInt())
            }
        } else {
            compartments.mapIndexed { i, s ->
                VehicleCompartment(vehicleCode, i.inc(), s.toInt())
            }
        }
    }
}