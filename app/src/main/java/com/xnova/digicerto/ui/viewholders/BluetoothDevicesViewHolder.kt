package com.xnova.digicerto.ui.viewholders

import android.graphics.Color
import com.xnova.digicerto.databinding.RowBluetoothDeviceBinding
import com.xnova.digicerto.models.BluetoothDevice

class BluetoothDevicesViewHolder(
    binding: RowBluetoothDeviceBinding,
    indicateUnpairedDevices: Boolean
) {

    private val mBinding = binding
    private val mIndicateUnpairedDevices = indicateUnpairedDevices
    private lateinit var mBluetoothDevice: BluetoothDevice

    fun bind(bluetoothDevice: BluetoothDevice) {
        mBluetoothDevice = bluetoothDevice
        setComponents()
    }

    private fun setComponents() {
        mBinding.textNameDevice.text = mBluetoothDevice.name
        mBinding.textMacDevice.text = mBluetoothDevice.mac

        if (mIndicateUnpairedDevices && !mBluetoothDevice.paired) {
            mBinding.textNameDevice.setTextColor(Color.RED)
        }
    }
}