package com.xnova.digicerto.services.sync.builders

import android.content.Context
import com.xnova.digicerto.models.Route
import com.xnova.digicerto.services.constants.SyncConstants
import com.xnova.digicerto.services.util.NumberUtil

class RouteBuilder(context: Context) : Build(context, "ROTA") {

    override fun validate(line: List<String>): Boolean {
        return if (
            line.count() >= SyncConstants.FTP.KEYS.ROUTE.MINIMUM_DATA &&
            NumberUtil.isInt(line[SyncConstants.FTP.KEYS.ROUTE.CODE]) &&
            line[SyncConstants.FTP.KEYS.ROUTE.NAME].isNotBlank() &&
            NumberUtil.isInt(line[SyncConstants.FTP.KEYS.ROUTE.SEQUENCE]) &&
            NumberUtil.isBlankOrInt(line[SyncConstants.FTP.KEYS.ROUTE.PRODUCER_CODE]) &&
            NumberUtil.isBlankOrInt(line[SyncConstants.FTP.KEYS.ROUTE.PRODUCER_FARM_CODE])
        ) {
            true
        } else {
            formatException()
        }
    }

    override fun build(line: List<String>): Route {
        return Route(
            code = line[SyncConstants.FTP.KEYS.ROUTE.CODE].toInt(),
            name = line[SyncConstants.FTP.KEYS.ROUTE.NAME].trim().uppercase()
        )
    }
}