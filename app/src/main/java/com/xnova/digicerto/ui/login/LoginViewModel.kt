package com.xnova.digicerto.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xnova.digicerto.services.repositories.local.SettingsRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val mSettingsRepository = SettingsRepository(application)

    private val mLogin = MutableLiveData<Boolean>()
    val login: LiveData<Boolean> = mLogin

    fun login(username: String, password: String) {
        val settings = mSettingsRepository.get()

        mLogin.value = settings.authentication!!.authenticate(username, password)
    }
}