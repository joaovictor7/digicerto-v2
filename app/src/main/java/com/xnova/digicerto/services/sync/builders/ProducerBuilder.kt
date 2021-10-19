package com.xnova.digicerto.services.sync.builders

import android.content.Context
import com.xnova.digicerto.models.entities.Producer
import com.xnova.digicerto.services.constants.SyncConstants
import com.xnova.digicerto.services.enums.producer.TankType
import com.xnova.digicerto.services.util.NumberUtil

class ProducerBuilder(mContext: Context) : Builder(mContext, REGISTER_TYPE) {

    companion object {
        const val REGISTER_TYPE = "PRODUTOR"
    }

    override fun validate(line: List<String>): Boolean {
        return if (
            line.count() >= SyncConstants.FTP.KEYS.PRODUCER.MINIMUM_DATA &&
            NumberUtil.isInt(line[SyncConstants.FTP.KEYS.PRODUCER.CODE]) &&
            line[SyncConstants.FTP.KEYS.PRODUCER.NAME].isNotBlank() &&
            NumberUtil.isBlankOrInt(line[SyncConstants.FTP.KEYS.PRODUCER.FARM_CODE]) &&
            NumberUtil.isBlankOrDouble(line[SyncConstants.FTP.KEYS.PRODUCER.AVG_VOLUME]) &&
            line[SyncConstants.FTP.KEYS.PRODUCER.TANK_TYPE].isNotBlank() &&
            TankType.existId(line[SyncConstants.FTP.KEYS.PRODUCER.TANK_TYPE]) &&
            NumberUtil.isBlankOrInt(line[SyncConstants.FTP.KEYS.PRODUCER.TANK_CODE])
        ) {
            true
        } else {
            formatException()
        }
    }

    override fun build(line: List<String>): Producer {
        val farmCode = line[SyncConstants.FTP.KEYS.PRODUCER.FARM_CODE].toIntOrNull() ?: 0
        val avgVolume = line[SyncConstants.FTP.KEYS.PRODUCER.AVG_VOLUME].toDoubleOrNull() ?: 0.0
        val tankCode = line[SyncConstants.FTP.KEYS.PRODUCER.TANK_CODE].toIntOrNull() ?: 0

        return Producer(
            code = line[SyncConstants.FTP.KEYS.PRODUCER.CODE].toInt(),
            farmCode = farmCode,
            name = line[SyncConstants.FTP.KEYS.PRODUCER.NAME].trim().uppercase(),
            farmName = line[SyncConstants.FTP.KEYS.PRODUCER.FARM_NAME].trim().uppercase()
                .ifBlank { null },
            avgVolume = if (avgVolume > 0) avgVolume else null,
            tankType = TankType.getById(line[SyncConstants.FTP.KEYS.PRODUCER.TANK_TYPE].trim()),
            tankCode = if (tankCode > 0) tankCode else null,
            note = line[SyncConstants.FTP.KEYS.PRODUCER.NOTE].trim().ifBlank { null }
        )
    }
}