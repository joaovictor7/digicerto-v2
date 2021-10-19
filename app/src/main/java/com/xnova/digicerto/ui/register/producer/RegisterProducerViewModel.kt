package com.xnova.digicerto.ui.register.producer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xnova.digicerto.services.repositories.local.entities.*
import com.xnova.digicerto.services.util.NumberUtil

class RegisterProducerViewModel(application: Application) : AndroidViewModel(application) {

    private val mProducers = ProducerRepository(application).getAllActive()

    var filteredProducers = mProducers
        private set

    private val mRefreshScreen = MutableLiveData<Boolean>()
    val refreshScreen: LiveData<Boolean> = mRefreshScreen

    fun filterProducers(producerCodeOrName: String) {
        filteredProducers = if (NumberUtil.isInt(producerCodeOrName)) {
            mProducers.filter { it.code.toString().contains(producerCodeOrName) }
        } else {
            mProducers.filter { it.name.contains(producerCodeOrName, true) }
        }
        mRefreshScreen.value = true
    }
}