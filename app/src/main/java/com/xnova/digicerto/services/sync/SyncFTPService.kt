package com.xnova.digicerto.services.sync

import android.content.Context
import com.xnova.digicerto.services.constants.SyncConstants
import com.xnova.digicerto.services.repositories.remote.FTPRepository
import com.xnova.digicerto.services.sync.builders.*
import io.reactivex.rxjava3.core.Observable
import java.io.File
import java.util.*

class SyncFTPService(private val mContext: Context) : SyncService(mContext) {

    private val mFTPSettings = mSettings.ftpSettings!!
    private val mFTPRepository = FTPRepository(mContext, mFTPSettings.host!!, mFTPSettings.port!!)

    override fun necessarySync(): Observable<Boolean> {
        return mFTPRepository.connect(mFTPSettings.username!!, mFTPSettings.getPasswordDecrypted())
            .flatMap { mFTPRepository.fileModificationDate(mFTPSettings.folder!! + SyncConstants.FTP.FILE_NAME) }
            .flatMap {
                mFTPRepository.dispose()
                necessarySync(it)
            }
    }

    override fun retrieveData(): Observable<Boolean> {
        return mFTPRepository.connect(mFTPSettings.username!!, mFTPSettings.getPasswordDecrypted())
            .flatMap {
                mFTPRepository.downloadFile(mFTPSettings.folder!!, SyncConstants.FTP.FILE_NAME)
            }
            .map {
                mFTPRepository.dispose()
                readAllLinesFile(it)
            }
            .map { inactiveAllRegisters() }
            .map { true }
    }

    override fun syncProducers(): Observable<Boolean> {
        return Observable.create { emitter ->
            mProducers.distinctBy { listOf(it.code, it.farmCode) }.forEach {
                mProducerRepository.addOrUpdate(it)
            }
            emitter.onNext(true)
            emitter.onComplete()
        }
    }

    override fun syncRoutes(): Observable<Boolean> {
        return Observable.create { emitter ->
            mRoutes.distinctBy { it.code }.forEach {
                mRouteRepository.addOrUpdate(it)
            }

            mRouteProducers.distinctBy {
                listOf(it.routeCode, it.producerCode, it.producerFarmCode)
            }.forEach {
                mRouteRepository.addOrUpdate(it)
            }
            emitter.onNext(true)
            emitter.onComplete()
        }
    }

    override fun syncVehicles(): Observable<Boolean> {
        return Observable.create { emitter ->
            mVehiclesWithCompartments.distinctBy { it.vehicle.code }.forEach { it ->
                mVehicleRepository.addOrUpdate(it.vehicle)

                it.compartments.forEach {
                    mVehicleRepository.addOrUpdate(it)
                }
            }
            emitter.onNext(true)
            emitter.onComplete()
        }
    }

    override fun syncOccurrences(): Observable<Boolean> {
        return Observable.create { emitter ->
            mOccurrences.distinctBy { it.code }.forEach {
                mOccurrenceRepository.addOrUpdate(it)
            }
            emitter.onNext(true)
            emitter.onComplete()
        }
    }

    override fun syncDrivers(): Observable<Boolean> {
        return Observable.create { emitter ->
            mDrivers.distinctBy { it.code }.forEach {
                mDriverRepository.addOrUpdate(it)
            }
            emitter.onNext(true)
            emitter.onComplete()
        }
    }

    private fun readAllLinesFile(fileDir: String) {
        val allLines = File(fileDir).readLines()
        allLines.forEach {
            val line = it.split(SyncConstants.FTP.SPLIT_DELIMITER)
            lineValidateAndEntitieBuilder(line)
        }
    }

    private fun necessarySync(lastModified: Calendar): Observable<Boolean> {
        return Observable.create {
            if (mSettings.latestSync == null || mSettings.latestSync!! < lastModified) {
                it.onNext(true)
            }

            it.onComplete()
        }
    }

    private fun lineValidateAndEntitieBuilder(line: List<String>) {
        var builder: Builder

        when (line[SyncConstants.FTP.KEYS.RECORD_TYPE]) {
            ProducerBuilder.REGISTER_TYPE -> {
                builder = ProducerBuilder(mContext)
                if (builder.validate(line)) {
                    mProducers.add(builder.build(line))
                }
            }
            VehicleBuilder.REGISTER_TYPE -> {
                builder = VehicleBuilder(mContext)
                if (builder.validate(line)) {
                    mVehiclesWithCompartments.add(builder.build(line))
                }
            }
            DriverBuilder.REGISTER_TYPE -> {
                builder = DriverBuilder(mContext)
                if (builder.validate(line)) {
                    mDrivers.add(builder.build(line))
                }
            }
            RouteBuilder.REGISTER_TYPE -> {
                builder = RouteBuilder(mContext)
                if (builder.validate(line)) {
                    mRoutes.add(builder.build(line))
                }

                builder = RouteProducerBuilder(mContext)
                if (builder.validate(line)) {
                    mRouteProducers.add(builder.build(line))
                }
            }
            OccurrenceBuilder.REGISTER_TYPE -> {
                builder = OccurrenceBuilder(mContext)
                if (builder.validate(line)) {
                    mOccurrences.add(builder.build(line))
                }
            }
        }
    }
}