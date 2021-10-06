package com.xnova.digicerto.ui.settings.printer

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.xnova.digicerto.R
import com.xnova.digicerto.databinding.ActivityPrinterSettingsBinding
import com.xnova.digicerto.models.BluetoothDevice
import com.xnova.digicerto.models.entities.settings.DataPrintSettings
import com.xnova.digicerto.models.entities.settings.PrinterDataSettings
import com.xnova.digicerto.models.entities.settings.PrinterSettings
import com.xnova.digicerto.services.adapters.BluetoothDevicesAdapter
import com.xnova.digicerto.services.enums.AlertType
import com.xnova.digicerto.services.enums.settings.printer.Dupplicate
import com.xnova.digicerto.services.enums.settings.printer.LayoutPrinter
import com.xnova.digicerto.services.factories.inputs.OnItemSelectedFactory
import com.xnova.digicerto.ui.BaseActivity

class PrinterSettingsActivity : BaseActivity(R.string.text_printer),
    CompoundButton.OnCheckedChangeListener {

    private lateinit var mBinding: ActivityPrinterSettingsBinding
    private lateinit var mViewModel: PrinterSettingsViewModel
    private lateinit var mBluetoohDeviceAdapter: BluetoothDevicesAdapter

    private val mBluetoothResultNone =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    private val mBluetoothResultTest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                mViewModel.printerTest()
            } else {
                showAlert(
                    AlertType.Info,
                    R.string.text_disabled_bluetooth,
                    R.string.msg_active_bluetooth
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProvider(this).get(PrinterSettingsViewModel::class.java)
        mBinding = ActivityPrinterSettingsBinding.inflate(layoutInflater)
        viewRoot = mBinding.root
        setContentView(viewRoot)

        listeners()
        observers()
        adapters()
        setComponents()
    }

    override fun onResume() {
        super.onResume()
        setBluetoothDevices()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.menu_save -> {
                if (componentsValidate()) {
                    getComponents()
                    mViewModel.save()
                    if (mViewModel.printerSettings.usePrinter) {
                        showPrinterTestAlert()
                    } else {
                        showSnackBar(R.string.text_settings_saved)
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView) {
            mBinding.switchUsePrinter -> {
                if (!isChecked) {
                    showMandatoryMsg(mBinding.textMandatoryPrinter, false)
                    setBluetoothDevices()
                } else {
                    requestEnableBluetooth(mBluetoothResultNone)
                }
            }
        }
    }

    private fun listeners() {
        mBinding.spinnerBluetoothPrinter.onItemSelectedListener = OnItemSelectedFactory.cleanError(
            mBinding.textMandatoryPrinter
        )

        mBinding.switchUsePrinter.setOnCheckedChangeListener(this)
    }

    private fun observers() {
        mViewModel.refreshScreen.observe(this, {
            setComponents()
            setBluetoothDevices()
        })

        mViewModel.showAlert.observe(this, {
            showAlert(it.alertType, it.titleId, it.messageId)
        })

        mViewModel.showProgressBar.observe(this, {
            if (it) {
                showProgressBar(R.string.msg_printer_testing)
            } else {
                hideProgressBar()
            }
        })
    }

    private fun adapters() {
        mBluetoohDeviceAdapter = BluetoothDevicesAdapter(this)
        mBinding.spinnerBluetoothPrinter.adapter = mBluetoohDeviceAdapter
    }

    private fun setComponents() {
        mBinding.switchUsePrinter.isChecked = mViewModel.printerSettings.usePrinter
        mBinding.switchCollectCancelled.isChecked = mViewModel.printerSettings.collectCancelled

        mBinding.checkPrintAlizarol.isChecked = mViewModel.printerSettings.dataPrint.printAlizarol
        mBinding.checkPrintCompartments.isChecked =
            mViewModel.printerSettings.dataPrint.printCompartments
        mBinding.checkPrintOccurrence.isChecked =
            mViewModel.printerSettings.dataPrint.printOccurrence
        mBinding.checkPrintSample.isChecked = mViewModel.printerSettings.dataPrint.printSample
        mBinding.checkPrintScale.isChecked = mViewModel.printerSettings.dataPrint.printScale
        mBinding.checkPrintTemperature.isChecked =
            mViewModel.printerSettings.dataPrint.printTemperature

        when (mViewModel.printerSettings.layoutPrinter) {
            LayoutPrinter.Complete -> mBinding.radioLayoutComplete.isChecked = true
            LayoutPrinter.SummedUp -> mBinding.radioLayoutSummedUp.isChecked = true
        }

        when (mViewModel.printerSettings.dupplicate) {
            Dupplicate.None -> mBinding.radioDupplicateNone.isChecked = true
            Dupplicate.Ever -> mBinding.radioDupplicateEver.isChecked = true
            Dupplicate.Optional -> mBinding.radioDupplicateOptional.isChecked = true
        }
    }

    private fun setBluetoothDevices() {
        val bluetoothDevices = mViewModel.getBluetoothDevices()
        mBluetoohDeviceAdapter.setBluetoothDevices(
            bluetoothDevices, mBinding.switchUsePrinter.isChecked
        )

        if (!mViewModel.bluetoothIsEnabled) {
            showHelp(mBinding.textPrinterHelp, R.string.msg_printer_bluetooth_off)
        } else if (bluetoothDevices.count() == 0) {
            showHelp(mBinding.textPrinterHelp, R.string.msg_printer_not_paread)
        } else {
            hideHelp(mBinding.textPrinterHelp)
        }

        mViewModel.printerSettings.printerData?.let {
            val position = mBluetoohDeviceAdapter.getPosition(it.macAddress!!)
            mBinding.spinnerBluetoothPrinter.setSelection(position, true)
        }
    }

    private fun getComponents() {
        val usePrinter = mBinding.switchUsePrinter.isChecked
        val collectCancelled = mBinding.switchCollectCancelled.isChecked
        val printer = mBinding.spinnerBluetoothPrinter.selectedItem as BluetoothDevice?
        val printerData = printer?.let {
            PrinterDataSettings(printer.name, printer.mac)
        }
        val layoutPrinter = when {
            mBinding.radioLayoutComplete.isChecked -> LayoutPrinter.Complete
            else -> LayoutPrinter.SummedUp
        }
        val dupplicate = when {
            mBinding.radioDupplicateNone.isChecked -> Dupplicate.None
            mBinding.radioDupplicateEver.isChecked -> Dupplicate.Ever
            else -> Dupplicate.Optional
        }
        val dataPrint = DataPrintSettings(
            mBinding.checkPrintScale.isChecked,
            mBinding.checkPrintCompartments.isChecked,
            mBinding.checkPrintSample.isChecked,
            mBinding.checkPrintTemperature.isChecked,
            mBinding.checkPrintOccurrence.isChecked,
            mBinding.checkPrintAlizarol.isChecked
        )

        val printerSettings = PrinterSettings(
            usePrinter,
            dupplicate,
            collectCancelled,
            layoutPrinter,
            printerData,
            dataPrint
        )
        mViewModel.printerSettings = printerSettings
    }

    private fun componentsValidate(): Boolean {
        var valid = true

        if (mBinding.switchUsePrinter.isChecked && mBinding.spinnerBluetoothPrinter.selectedItem == null) {
            valid = false
            showMandatoryMsg(mBinding.textMandatoryPrinter, true)
        }

        return valid
    }

    private fun showPrinterTestAlert() {
        alertFactory.getInstance(R.string.text_settings_saved, R.string.msg_test_printer,
            actionPositive = { dialog, _ ->
                dialog.dismiss()
                if (mViewModel.bluetoothIsEnabled) {
                    mViewModel.printerTest()
                } else {
                    requestEnableBluetooth(mBluetoothResultTest)
                }
            },
            actionNegative = { dialog, _ ->
                dialog.dismiss()
            }).show()
    }

    private fun requestEnableBluetooth(intent: ActivityResultLauncher<Intent>) {
        if (!mViewModel.bluetoothIsEnabled) {
            intent.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
        }
    }
}