package com.xnova.digicerto.services.sync.builders

import android.content.Context
import com.xnova.digicerto.models.entities.Occurrence
import com.xnova.digicerto.services.constants.SyncConstants
import com.xnova.digicerto.services.enums.OccurrenceType
import com.xnova.digicerto.services.util.NumberUtil

class OccurrenceBuilder(context: Context) : Builder(context, REGISTER_TYPE) {

    companion object {
        const val REGISTER_TYPE = "OCORRENCIA"
    }

    override fun validate(line: List<String>): Boolean {
        return if (
            line.count() >= SyncConstants.FTP.KEYS.OCCURRENCE.MINIMUM_DATA &&
            NumberUtil.isInt(line[SyncConstants.FTP.KEYS.OCCURRENCE.CODE]) &&
            line[SyncConstants.FTP.KEYS.OCCURRENCE.TYPE].isNotBlank() &&
            OccurrenceType.existId(line[SyncConstants.FTP.KEYS.OCCURRENCE.TYPE]) &&
            line[SyncConstants.FTP.KEYS.OCCURRENCE.DESCRIPTION].isNotBlank()
        ) {
            true
        } else {
            formatException()
        }
    }

    override fun build(line: List<String>): Occurrence {
        return Occurrence(
            code = line[SyncConstants.FTP.KEYS.OCCURRENCE.CODE].toInt(),
            type = OccurrenceType.getById(line[SyncConstants.FTP.KEYS.OCCURRENCE.TYPE].trim()),
            description = line[SyncConstants.FTP.KEYS.OCCURRENCE.DESCRIPTION].trim().uppercase()
        )
    }
}