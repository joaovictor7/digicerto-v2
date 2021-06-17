package com.xnova.digicerto.ui.splashscreen

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xnova.digicerto.R
import com.xnova.digicerto.services.repository.remote.FTPRepository

class SplashScreenViewModel(application: Application) : AndroidViewModel(application) {
    private var mAction = MutableLiveData<String>()
    val action: LiveData<String> = mAction

    fun start() {
        downloadFTPFile();
    }

    private fun downloadFTPFile() {
        mAction.value = getApplication<Application>().getString(R.string.msg_update_registers)
        val ftpRepository = FTPRepository(getApplication<Application>(), "187.45.193.203", 21)
        ftpRepository.connect("xnova", "semparar9")
            .subscribe()

        ftpRepository.downloadFile("/teste/DIGICERTO.txt", "DIGICERTO.txt")
            .subscribe(
                { Toast.makeText(getApplication(), "deu certo", Toast.LENGTH_LONG).show() },
                { e ->
                    Toast.makeText(getApplication(), "deu errado", Toast.LENGTH_LONG).show()
                })
    }
}