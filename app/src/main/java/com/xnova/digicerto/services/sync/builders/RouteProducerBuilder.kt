package com.xnova.digicerto.services.sync.builders

import android.content.Context
import com.xnova.digicerto.models.RouteProducer
import com.xnova.digicerto.services.constants.SyncConstants
import com.xnova.digicerto.services.util.NumberUtil

class RouteProducerBuilder(context: Context) : Build(context, "ROTA") {

    override fun validate(line: List<String>): Boolean {
        return line.count() >= SyncConstants.FTP.KEYS.ROUTE.MINIMUM_DATA_WITH_PRODUCER &&
                NumberUtil.isInt(line[SyncConstants.FTP.KEYS.ROUTE.CODE]) &&
                NumberUtil.isInt(line[SyncConstants.FTP.KEYS.ROUTE.PRODUCER_CODE]) &&
                NumberUtil.isBlankOrInt(line[SyncConstants.FTP.KEYS.ROUTE.PRODUCER_FARM_CODE]) &&
                NumberUtil.isBlankOrInt(line[SyncConstants.FTP.KEYS.ROUTE.SEQUENCE])
    }

    override fun build(line: List<String>): RouteProducer {
        val producerFarmCode =
            line[SyncConstants.FTP.KEYS.ROUTE.PRODUCER_FARM_CODE].toIntOrNull() ?: 0
        val sequence = line[SyncConstants.FTP.KEYS.ROUTE.SEQUENCE].toIntOrNull() ?: 0

        return RouteProducer(
            routeCode = line[SyncConstants.FTP.KEYS.ROUTE.CODE].toInt(),
            producerCode = line[SyncConstants.FTP.KEYS.ROUTE.PRODUCER_CODE].toInt(),
            producerFarmCode = if (producerFarmCode > 0) producerFarmCode else 1,
            sequence = sequence
        )
    }
}