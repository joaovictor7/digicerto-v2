package com.xnova.digicerto.ui.register.route

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xnova.digicerto.services.repositories.local.entities.*
import com.xnova.digicerto.services.util.NumberUtil

class RegisterRouteViewModel(application: Application) : AndroidViewModel(application) {

    private val mRoutes = RouteRepository(application).getAllActive()

    var filteredRoutes = mRoutes
        private set

    private val mRefreshScreen = MutableLiveData<Boolean>()
    val refreshScreen: LiveData<Boolean> = mRefreshScreen

    fun filterRoutes(vehicleCodeOrPlate: String) {
        filteredRoutes = if (NumberUtil.isInt(vehicleCodeOrPlate)) {
            mRoutes.filter { it.route.code.toString().contains(vehicleCodeOrPlate) }
        } else {
            mRoutes.filter { it.route.name.contains(vehicleCodeOrPlate, true) }
        }
        mRefreshScreen.value = true
    }
}