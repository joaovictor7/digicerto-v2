package com.xnova.digicerto.ui.splashscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xnova.digicerto.R
import com.xnova.digicerto.models.settings.FTPSettings
import com.xnova.digicerto.services.enums.OperationType
import com.xnova.digicerto.services.data.DatabaseService
import com.xnova.digicerto.services.repositories.remote.FTPRepository
import com.xnova.digicerto.services.sync.SyncFTPService
import com.xnova.digicerto.services.sync.SyncService
import com.xnova.digicerto.services.sync.SyncWSService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class SplashScreenViewModel(application: Application) : AndroidViewModel(application) {
    private val mDatabaseRepository = DatabaseService.getDatabase(application)
    private lateinit var mSyncService: SyncService

    private var mAction = MutableLiveData<String>()
    val action: LiveData<String> = mAction

    private var mNextPage = MutableLiveData<Boolean>()
    val nextPage: LiveData<Boolean> = mNextPage

    fun start() {
        temporary()
        Observable.concatArray(syncData())
            .subscribe({ }, { }, {
                mNextPage.value = true
            })
    }

    private fun syncData(): Observable<Boolean> {
        val settings = mDatabaseRepository.settingsDao().get()
        if (!settings.operationTypeAvailable) {
            throw Exception()
        }

        if (settings.operationType == OperationType.FTP) {
            if (settings.ftpOperationAvailable) {
                return syncDataFTP()
            }
        } else {
            mSyncService = SyncWSService(getApplication())
        }

        return Observable.error(Exception())
    }

    private fun syncDataFTP(): Observable<Boolean> {
        mSyncService = SyncFTPService(getApplication())
        return mSyncService.necessarySync()
            .flatMap {
                mAction.postValue(getApplication<Application>().getString(R.string.msg_update_registers))
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
        val settings = mDatabaseRepository.settingsDao().get()
        settings.operationType = OperationType.FTP
        settings.ftpSettings = FTPSettings("187.45.193.203", 21, "xnova", "semparar9", "/teste/")
        mDatabaseRepository.settingsDao().update(settings)
    }
}