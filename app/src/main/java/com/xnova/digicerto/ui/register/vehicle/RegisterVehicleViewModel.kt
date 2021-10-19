package com.xnova.digicerto.ui.register.vehicle

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xnova.digicerto.services.repositories.local.entities.*
import com.xnova.digicerto.services.util.NumberUtil

class RegisterVehicleViewModel(application: Application) : AndroidViewModel(application) {

    private val mVehicles = VehicleRepository(application).getAllActive()

    var filteredVehicles = mVehicles
        private set

    private val mRefreshScreen = MutableLiveData<Boolean>()
    val refreshScreen: LiveData<Boolean> = mRefreshScreen

    fun filterVehicles(vehicleCodeOrPlate: String) {
        filteredVehicles = if (NumberUtil.isInt(vehicleCodeOrPlate)) {
            mVehicles.filter { it.vehicle.code.toString().contains(vehicleCodeOrPlate) }
        } else {
            mVehicles.filter { it.vehicle.plate.contains(vehicleCodeOrPlate, true) }
        }
        mRefreshScreen.value = true
    }
}