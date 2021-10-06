package com.xnova.digicerto.ui.settings.printer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xnova.digicerto.R
import com.xnova.digicerto.models.Alert
import com.xnova.digicerto.models.BluetoothDevice
import com.xnova.digicerto.services.enums.AlertType
import com.xnova.digicerto.services.repositories.local.PrinterRepository
import com.xnova.digicerto.services.repositories.local.entities.SettingsRepository
import com.xnova.digicerto.services.repositories.remote.BluetoothRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class PrinterSettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val mSettingsRepository = SettingsRepository(application)
    private val mBluetoothRepository = BluetoothRepository()
    private val mPrinterUtil = PrinterRepository(application)
    private var mSettings = mSettingsRepository.get()

    var printerSettings
        get() = mSettings.printerSettings
        set(value) {
            mSettings.printerSettings = value
        }

    val bluetoothIsEnabled
        get() = mBluetoothRepository.isEnabled

    private val mRefreshScreen = MutableLiveData<Boolean>()
    val refreshScreen: LiveData<Boolean> = mRefreshScreen

    private val mShowAlert = MutableLiveData<Alert>()
    val showAlert: LiveData<Alert> = mShowAlert

    private val mShowProgressBar = MutableLiveData<Boolean>()
    val showProgressBar: LiveData<Boolean> = mShowProgressBar

    fun save() {
        mSettingsRepository.update(mSettings)
        refreshEntities()
    }

    fun getBluetoothDevices(): List<BluetoothDevice> {
        val bluetoothDevices = mBluetoothRepository.pairedDevices.toList()
        return BluetoothDevice.convertTo(bluetoothDevices, mSettings.printerSettings.printerData)
    }

    fun printerTest() {
        val printer =
            mBluetoothRepository.pairedDevices.find { it.address == printerSettings.printerData!!.macAddress }
        if (printer == null) {
            mShowAlert.value =
                Alert(AlertType.Error, R.string.text_failure, R.string.msg_printer_not_paired)
            return
        }

        showProgressBar(true)
        mBluetoothRepository.printTextBluetoothPrinter(printer, mPrinterUtil.getTestText())
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                showProgressBar(false)
                mShowAlert.value =
                    Alert(AlertType.Success, R.string.text_success, R.string.msg_printer_working)
            }, {
                showProgressBar(false)
                mShowAlert.value =
                    Alert(AlertType.Error, R.string.text_failure, R.string.msg_printer_not_working)
            })
    }

    private fun refreshEntities() {
        mSettings = mSettingsRepository.get()
        mRefreshScreen.value = true
    }

    private fun showProgressBar(show: Boolean) {
        mShowProgressBar.value = show
    }
}