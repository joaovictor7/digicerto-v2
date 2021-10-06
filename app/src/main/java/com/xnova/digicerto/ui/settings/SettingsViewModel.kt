package com.xnova.digicerto.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xnova.digicerto.R
import com.xnova.digicerto.models.MenuSettings
import com.xnova.digicerto.services.constants.SettingsConstants
import com.xnova.digicerto.services.enums.settings.OperationType
import com.xnova.digicerto.services.repositories.local.entities.SettingsRepository

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
        ),
        MenuSettings(
            SettingsConstants.MENU.FILE_ID,
            application.getString(R.string.text_file),
            application.getString(R.string.msg_description_file)
        )
    )

    private val mApplication = application
    private val mSettingsRepository = SettingsRepository(application)
    private val mRefreshScreen = MutableLiveData<Boolean>()
    val refreshScreen: LiveData<Boolean> = mRefreshScreen

    var settings = mSettingsRepository.get()
        private set

    fun getMenuSettings(): List<MenuSettings> {
        val menuList = mMenuSettings.toMutableList()

        if (settings.operationType == OperationType.FTP) {
            menuList.add(
                MenuSettings(
                    SettingsConstants.MENU.FTP_ID,
                    mApplication.getString(R.string.text_FTP),
                    mApplication.getString(R.string.msg_description_FTP)
                )
            )
        } else if (settings.operationType == OperationType.WebService) {
            menuList.add(
                MenuSettings(
                    SettingsConstants.MENU.WS_ID,
                    mApplication.getString(R.string.text_web_service),
                    mApplication.getString(R.string.msg_description_web_service)
                )
            )
        }

        return menuList
    }

    fun setOperationType(operationType: OperationType) {
        settings.operationType = operationType
    }

    fun save() {
        mSettingsRepository.update(settings)
        refreshEntities()
    }

    fun refreshEntities() {
        settings = mSettingsRepository.get()
        mRefreshScreen.value = true
    }
}