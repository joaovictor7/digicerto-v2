package com.xnova.digicerto.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.xnova.digicerto.databinding.RowBluetoothDeviceBinding
import com.xnova.digicerto.models.BluetoothDevice
import com.xnova.digicerto.ui.viewholders.BluetoothDevicesViewHolder

class BluetoothDeviceAdapter(private val mContext: Context) : BaseAdapter() {

    private var mBluetoothDevices = listOf<BluetoothDevice>()
    private var mIndicateUnpairedDevices = true

    override fun getCount(): Int {
        return mBluetoothDevices.size
    }

    override fun getItem(position: Int): Any {
        return mBluetoothDevices[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = if (convertView == null) {
            RowBluetoothDeviceBinding.inflate(LayoutInflater.from(mContext), parent, false)
        } else {
            RowBluetoothDeviceBinding.bind(convertView)
        }

        val viewHolder = BluetoothDevicesViewHolder(binding, mIndicateUnpairedDevices)
        viewHolder.bind(mBluetoothDevices[position])

        return binding.root
    }

    fun setBluetoothDevices(
        bluetoothDevices: List<BluetoothDevice>,
        indicateUnpairedDevices: Boolean
    ) {
        mBluetoothDevices = bluetoothDevices.sortedBy { it.name }
        mIndicateUnpairedDevices = indicateUnpairedDevices
        notifyDataSetChanged()
    }

    fun getPosition(mac: String): Int {
        return mBluetoothDevices.indexOfFirst { it.mac == mac }
    }
}