package com.xnova.digicerto.ui.settings

import android.app.Application
import android.view.Menu
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xnova.digicerto.R
import com.xnova.digicerto.models.MenuSettings
import com.xnova.digicerto.models.entities.settings.Settings
import com.xnova.digicerto.services.constants.SettingsConstants
import com.xnova.digicerto.services.enums.OperationType
import com.xnova.digicerto.services.repositories.local.SettingsRepository

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val mMenuSettings = listOf(
        MenuSettings(
            SettingsConstants.MENU.TRAVEL_ID,
            application.getString(R.string.text_travel),
            application.getString(R.string.msg_description_travel)
        ),
        MenuSettings(
            SettingsConstants.MENU.PRINTER_ID,
            application.getString(R.string.text_printer),
            application.getString(R.string.msg_description_printer)
        ),
        MenuSettings(
            SettingsConstants.MENU.APPLICATION_ID,
            application.getString(R.string.text_application),
            application.getString(R.string.msg_description_application)
        ),
        MenuSettings(
            SettingsConstants.MENU.COLLECT_ID,
            application.getString(R.string.text_collect),
            application.getString(R.string.msg_description_collect)
        )
    )

    private val mApplication = application
    private val mSettingsRepository = SettingsRepository(application)
    private var mSettings = mSettingsRepository.get()

    private val mSettingsListPair = MutableLiveData<List<MenuSettings>>()
    val settingsListPair: LiveData<List<MenuSettings>> = mSettingsListPair

    fun updateEntities() {
        mSettings = mSettingsRepository.get()
    }

    fun loginAvailable(): Boolean {
        return mSettings.authenticationAvailable
    }

    fun loadSettings() {
        mSettingsListPair.value = getMenuSettings().sortedBy { it.title }
    }

    fun necessaryChooseTypeOperation(): Boolean {
        return mSettings.necessaryChooseTypeOperation
    }

    fun setOperationType(operationType: OperationType) {
        mSettings.operationType = operationType
        mSettingsRepository.update(mSettings)
    }

    private fun getMenuSettings(): List<MenuSettings> {
        val listMenu = mMenuSettings.toMutableList()
        return when (mSettings.operationType) {
            OperationType.FTP -> {
                listMenu.add(
                    MenuSettings(
                        SettingsConstants.MENU.FTP_ID,
                        mApplication.getString(R.string.text_FTP),
                        mApplication.getString(R.string.msg_description_FTP)
                    )
                )
                listMenu
            }
            else -> {
                listMenu.add(
                    MenuSettings(
                        SettingsConstants.MENU.WS_ID,
                        mApplication.getString(R.string.text_web_service),
                        mApplication.getString(R.string.msg_description_web_service)
                    )
                )
                listMenu
            }
        }
    }
}