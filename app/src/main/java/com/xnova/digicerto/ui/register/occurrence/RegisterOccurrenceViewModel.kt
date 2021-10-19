package com.xnova.digicerto.ui.register.occurrence

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xnova.digicerto.services.repositories.local.entities.*
import com.xnova.digicerto.services.util.NumberUtil

class RegisterOccurrenceViewModel(application: Application) : AndroidViewModel(application) {

    private val mOccurrences = OccurrenceRepository(application).getAllActive()

    var filteredOccurrences = mOccurrences
        private set

    private val mRefreshScreen = MutableLiveData<Boolean>()
    val refreshScreen: LiveData<Boolean> = mRefreshScreen

    fun filterOccurrences(occurrenceCodeOrName: String) {
        filteredOccurrences = if (NumberUtil.isInt(occurrenceCodeOrName)) {
            mOccurrences.filter { it.code.toString().contains(occurrenceCodeOrName) }
        } else {
            mOccurrences.filter { it.description.contains(occurrenceCodeOrName, true) }
        }
        mRefreshScreen.value = true
    }
}