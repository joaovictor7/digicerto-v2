package com.xnova.digicerto.services.sync.builders

import android.content.Context
import com.xnova.digicerto.models.entities.Driver
import com.xnova.digicerto.services.constants.SyncConstants
import com.xnova.digicerto.services.util.NumberUtil

class DriverBuilder(context: Context) : Builder(context, REGISTER_TYPE) {

    companion object {
        const val REGISTER_TYPE = "TRANSPORTADOR"
    }

    override fun validate(line: List<String>): Boolean {
        return if (
            line.count() >= SyncConstants.FTP.KEYS.DRIVER.MINIMUM_DATA &&
            NumberUtil.isInt(line[SyncConstants.FTP.KEYS.DRIVER.CODE]) &&
            line[SyncConstants.FTP.KEYS.DRIVER.NAME].isNotBlank()
        ) {
            true
        } else {
            formatException()
        }
    }

    override fun build(line: List<String>): Driver {
        return Driver(
            code = line[SyncConstants.FTP.KEYS.DRIVER.CODE].toInt(),
            name = line[SyncConstants.FTP.KEYS.DRIVER.NAME].trim().uppercase()
        )
    }
}