package com.xnova.digicerto.services.sync

import android.content.Context
import io.reactivex.rxjava3.core.Observable

class SyncWSService(private val mContext: Context) : SyncService(mContext) {
    override fun necessarySync(): Observable<Boolean> {
        TODO("Not yet implemented")
    }

    override fun retrieveData(): Observable<Boolean> {
        TODO("Not yet implemented")
    }

    override fun syncProducers(): Observable<Boolean> {
        TODO("Not yet implemented")
    }

    override fun syncRoutes(): Observable<Boolean> {
        TODO("Not yet implemented")
    }

    override fun syncVehicles(): Observable<Boolean> {
        TODO("Not yet implemented")
    }

    override fun syncOccurrences(): Observable<Boolean> {
        TODO("Not yet implemented")
    }

    override fun syncDrivers(): Observable<Boolean> {
        TODO("Not yet implemented")
    }

}