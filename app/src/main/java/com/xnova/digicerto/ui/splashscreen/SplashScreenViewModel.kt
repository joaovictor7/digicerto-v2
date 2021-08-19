package com.xnova.digicerto.ui.splashscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xnova.digicerto.R
import com.xnova.digicerto.models.entities.settings.AuthenticationSettings
import com.xnova.digicerto.models.entities.settings.FTPSettings
import com.xnova.digicerto.services.enums.OperationType
import com.xnova.digicerto.services.data.DatabaseService
import com.xnova.digicerto.services.sync.SyncFTPService
import com.xnova.digicerto.services.sync.SyncService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class SplashScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application
    private val mDatabaseRepository = DatabaseService.getDatabase(application)
    private lateinit var mSyncService: SyncService

    private var mAction = MutableLiveData<String>()
    val action: LiveData<String> = mAction

    private var mNextPage = MutableLiveData<Boolean>()
    val nextPage: LiveData<Boolean> = mNextPage

    fun start() {
        temporary()
        Observable.concatArray(syncData())
            .subscribe({ }, {
                mAction.value = mContext.getString(R.string.text_update_failure)
                mNextPage.value = true
            }, {
                mNextPage.value = true
            })
    }

    private fun syncData(): Observable<Boolean> {
        val settings = mDatabaseRepository.settingsDao().get()
        if (!settings.operationTypeAvailable) {
            return Observable.empty()
        }

        return if (settings.operationType == OperationType.FTP) syncDataFTP() else syncDataWS()
    }

    private fun syncDataFTP(): Observable<Boolean> {
        mSyncService = SyncFTPService(getApplication())
        return mSyncService.necessarySync()
            .flatMap {
                mAction.postValue(getApplication<Application>().getString(R.string.text_update_registers))
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
                mAction.postValue(getApplication<Application>().getString(R.string.text_update_registers))
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

        val ftpSettings = FTPSettings("187.45.193.203", 21, "xnova", "/teste/")
        ftpSettings.setPasswordDecrypted("semparar9")
        settings.ftpSettings = ftpSettings

        val authentication =
            AuthenticationSettings("1").apply { setPasswordDecrypted("1") }
        settings.authentication = authentication

        mDatabaseRepository.settingsDao().update(settings)
    }
}