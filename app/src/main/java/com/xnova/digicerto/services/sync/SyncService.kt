package com.xnova.digicerto.services.sync

import android.content.Context
import com.xnova.digicerto.models.entities.*
import com.xnova.digicerto.models.entities.relations.VehicleWithCompartments
import com.xnova.digicerto.services.repositories.local.*
import io.reactivex.rxjava3.core.Observable

abstract class SyncService(context: Context) {

    protected val mProducerRepository = ProducerRepository(context)
    protected val mVehicleRepository = VehicleRepository(context)
    protected val mRouteRepository = RouteRepository(context)
    protected val mOccurrenceRepository = OccurrenceRepository(context)
    protected val mDriverRepository = DriverRepository(context)

    protected val mProducers: MutableList<Producer> = mutableListOf()
    protected val mVehiclesWithCompartments: MutableList<VehicleWithCompartments> = mutableListOf()
    protected val mRoutes: MutableList<Route> = mutableListOf()
    protected val mRouteProducers: MutableList<RouteProducer> = mutableListOf()
    protected val mOccurrences: MutableList<Occurrence> = mutableListOf()
    protected val mDrivers: MutableList<Driver> = mutableListOf()

    private val mSettingsRepository = SettingsRepository(context)
    protected val mSettings = mSettingsRepository.get()

    abstract fun necessarySync(): Observable<Boolean>
    abstract fun retrieveData(): Observable<Boolean>
    abstract fun syncProducers(): Observable<Boolean>
    abstract fun syncRoutes(): Observable<Boolean>
    abstract fun syncVehicles(): Observable<Boolean>
    abstract fun syncOccurrences(): Observable<Boolean>
    abstract fun syncDrivers(): Observable<Boolean>

    fun updateSyncData(): Observable<Boolean> {
        return Observable.create { emitter ->
            mSettings.updateLatestSync()
            mSettingsRepository.update(mSettings)
            emitter.onNext(true)
            emitter.onComplete()
        }
    }

    protected fun inactiveAllRegisters() {
        mProducerRepository.inactiveAll()
        mVehicleRepository.inactiveAll()
        mRouteRepository.inactiveAll()
        mOccurrenceRepository.inactiveAll()
        mDriverRepository.inactiveAll()
    }
}