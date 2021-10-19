package com.xnova.digicerto.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.xnova.digicerto.models.entities.Credential
import com.xnova.digicerto.models.entities.settings.FTPSettings
import com.xnova.digicerto.services.enums.settings.OperationType
import com.xnova.digicerto.services.repositories.local.entities.CredentialRepository
import com.xnova.digicerto.services.repositories.local.entities.SettingsRepository
import com.xnova.digicerto.services.sync.SyncFTPService
import com.xnova.digicerto.services.sync.SyncService
import com.xnova.digicerto.services.util.NetworkUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mApplication = application
    private val mSettings = SettingsRepository(application).get()
    private lateinit var mSyncService: SyncService

    var pageIsLoaded = false
        private set

    fun start() {
        temporary()
        Observable.concatArray(syncData())
            .subscribe({}, {
                pageIsLoaded = true
            }, {
                pageIsLoaded = true
            })
    }

    private fun syncData(): Observable<Boolean> {
        if (!mSettings.operationTypeAvailable && !NetworkUtil.isOnline(mApplication)) {
            return Observable.empty()
        }

        return if (mSettings.operationType == OperationType.FTP && mSettings.ftpOperationAvailable) {
            syncDataFTP()
        } else if (mSettings.wsOperationAvailable) {
            syncDataWS()
        } else {
            Observable.empty()
        }
    }

    private fun syncDataFTP(): Observable<Boolean> {
        mSyncService = SyncFTPService(mApplication)
        return mSyncService.necessarySync()
            .flatMap {
                mSyncService.retrieveData()
            }
            .flatMap { mSyncService.syncProducers() }
            .flatMap { mSyncService.syncRoutes() }
            .flatMap { mSyncService.syncVehicles() }
            .flatMap { mSyncService.syncDrivers() }
            .flatMap { mSyncService.syncOccurrences() }
            .flatMap { mSyncService.updateSyncData() }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun syncDataWS(): Observable<Boolean> {
        //mSyncService = SyncWSService(getApplication())
        mSyncService = SyncFTPService(getApplication())
        return mSyncService.necessarySync()
            .flatMap {
                mSyncService.retrieveData()
            }
            .flatMap { mSyncService.syncProducers() }
            .flatMap { mSyncService.syncRoutes() }
            .flatMap { mSyncService.syncVehicles() }
            .flatMap { mSyncService.syncDrivers() }
            .flatMap { mSyncService.syncOccurrences() }
            .flatMap { mSyncService.updateSyncData() }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun temporary() {
        val authentication =
            Credential("1").apply { setPasswordDecrypted("1") }

        CredentialRepository(mApplication).addOrUpdate(authentication)

        val ftp = FTPSettings("187.45.193.203", 21, "xnova").apply {
            setPasswordDecrypted("semparar9")
            setUnformattedFolder("teste")
        }

        val s = SettingsRepository(mApplication).get()
        s.ftpSettings = ftp
        s.operationType = OperationType.FTP

        SettingsRepository(mApplication).update(s)
    }
}