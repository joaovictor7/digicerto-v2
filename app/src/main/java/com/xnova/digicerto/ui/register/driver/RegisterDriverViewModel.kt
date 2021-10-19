package com.xnova.digicerto.ui.register.driver

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xnova.digicerto.services.repositories.local.entities.*
import com.xnova.digicerto.services.util.NumberUtil

class RegisterDriverViewModel(application: Application) : AndroidViewModel(application) {

    private val mDrivers = DriverRepository(application).getAllActive()

    var filteredDrivers = mDrivers
        private set

    private val mRefreshScreen = MutableLiveData<Boolean>()
    val refreshScreen: LiveData<Boolean> = mRefreshScreen

    fun filterDrivers(driverCodeOrName: String) {
        filteredDrivers = if (NumberUtil.isInt(driverCodeOrName)) {
            mDrivers.filter { it.code.toString().contains(driverCodeOrName) }
        } else {
            mDrivers.filter { it.name.contains(driverCodeOrName, true) }
        }
        mRefreshScreen.value = true
    }
}