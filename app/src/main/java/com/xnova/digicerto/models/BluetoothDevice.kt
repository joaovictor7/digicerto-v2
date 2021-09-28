package com.xnova.digicerto.models

import android.bluetooth.BluetoothDevice
import com.xnova.digicerto.models.entities.settings.PrinterDataSettings

data class BluetoothDevice(
    val name: String,
    val mac: String,
    val paired: Boolean = true
) {

    companion object {
        fun convertTo(
            bluetoothDevices: List<BluetoothDevice>, printerData: PrinterDataSettings?
        ): List<com.xnova.digicerto.models.BluetoothDevice> {
            val adapters =
                bluetoothDevices.map { BluetoothDevice(it.name, it.address) }.toMutableSet()

            printerData?.let {
                if (!bluetoothDevices.any { it.address == printerData.macAddress }) {
                    val notParead = BluetoothDevice(
                        printerData.printer!!,
                        printerData.macAddress!!,
                        false
                    )
                    adapters.add(notParead)
                }
            }

            return adapters.toList()
        }
    }
}