package com.xnova.digicerto.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xnova.digicerto.R
import com.xnova.digicerto.services.enums.OperationType
import com.xnova.digicerto.services.repositories.local.SettingsRepository

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val mMenuSettings = listOf(
        Pair(
            application.getString(R.string.text_travel),
            application.getString(R.string.msg_description_travel)
        ),
        Pair(
            application.getString(R.string.text_printer),
            application.getString(R.string.msg_description_printer)
        ),
        Pair(
            application.getString(R.string.text_application),
            application.getString(R.string.msg_description_application)
        )
    )

    private val mApplication = application
    private val mSettingsRepository = SettingsRepository(application)
    private val mSettings = mSettingsRepository.get()

    private val mSettingsListPair = MutableLiveData<List<Pair<String, String>>>()
    val settingsListPair: LiveData<List<Pair<String, String>>> = mSettingsListPair

    fun loginAvailable(): Boolean {
        return mSettings.authenticationAvailable
    }

    fun loadSettings() {
        mSettingsListPair.value = getMenuSettings().sortedBy { it.first }
    }

    fun necessaryChooseTypeOperation(): Boolean {
        return mSettings.necessaryChooseTypeOperation
    }

    fun setOperationType(operationType: OperationType) {
        mSettings.operationType = operationType
        mSettingsRepository.update(mSettings)
    }

    private fun getMenuSettings(): List<Pair<String, String>> {
        val listMenu = mMenuSettings.toMutableList()
        return when (mSettings.operationType) {
            OperationType.WebService -> {
                listMenu.add(
                    Pair(
                        mApplication.getString(R.string.text_web_service),
                        mApplication.getString(R.string.msg_description_web_service)
                    )
                )
                listMenu
            }
            OperationType.FTP -> {
                listMenu.add(
                    Pair(
                        mApplication.getString(R.string.text_FTP),
                        mApplication.getString(R.string.msg_description_FTP)
                    )
                )
                listMenu
            }
            else -> listMenu
        }
    }
}