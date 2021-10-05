package com.xnova.digicerto.services.repositories.remote

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import io.reactivex.rxjava3.core.Completable
import java.util.*

class BluetoothRepository {

    companion object {
        private const val MILLISECONDS_DELAY = 5000L
        private val mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    }

    private val mBluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    val pairedDevices: MutableSet<BluetoothDevice>
        get() = mBluetoothAdapter.bondedDevices

    val isEnabled: Boolean
        get() = mBluetoothAdapter.isEnabled

    fun printTextBluetoothPrinter(printer: BluetoothDevice, texts: List<String>): Completable {
        return Completable.create {
            texts.forEachIndexed { i, text ->
                val socket = printer.createRfcommSocketToServiceRecord(mUUID)
                val outputStream = socket.outputStream

                socket.connect()
                outputStream.write(text.toByteArray())
                outputStream.flush()

                outputStream.close()
                socket.close()
                delay(i, texts.count())
            }

            it.onComplete()
        }
    }

    private fun delay(index: Int, count: Int) {
        if (index.inc() != count) {
            Thread.sleep(MILLISECONDS_DELAY)
        }
    }
}