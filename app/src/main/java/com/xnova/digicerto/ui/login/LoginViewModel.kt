package com.xnova.digicerto.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xnova.digicerto.services.repositories.local.entities.CredentialRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val mCredentialRepository = CredentialRepository(application)
    private val mCredential = mCredentialRepository.get()

    private val mLogin = MutableLiveData<Boolean>()
    val login: LiveData<Boolean> = mLogin

    fun loginAvailable() = mCredential != null

    fun login(username: String, password: String) {
        mLogin.value = mCredential?.authenticate(username, password)
    }
}