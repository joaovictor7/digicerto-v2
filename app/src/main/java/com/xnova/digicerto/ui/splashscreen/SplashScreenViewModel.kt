package com.xnova.digicerto.ui.splashscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xnova.digicerto.R
import com.xnova.digicerto.models.entities.Credential
import com.xnova.digicerto.services.enums.settings.OperationType
import com.xnova.digicerto.services.repositories.local.entities.CredentialRepository
import com.xnova.digicerto.services.repositories.local.entities.SettingsRepository
import com.xnova.digicerto.services.sync.SyncFTPService
import com.xnova.digicerto.services.sync.SyncService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class SplashScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val mApplication = application
    private val mSettings = SettingsRepository(application).get()
    private lateinit var mSyncService: SyncService

    private var mMsgAction = MutableLiveData<Pair<Boolean, String>>()
    val msgAction: LiveData<Pair<Boolean, String>> = mMsgAction

    private var mNextPage = MutableLiveData<Boolean>()
    val nextPage: LiveData<Boolean> = mNextPage

    fun start() {
        temporary()
        Observable.concatArray(syncData())
            .subscribe({ }, {
                mMsgAction.value = Pair(false, mApplication.getString(R.string.text_failure))
                mNextPage.value = true
            }, {
                mNextPage.value = true
            })
    }

    private fun syncData(): Observable<Boolean> {
        if (!mSettings.operationTypeAvailable) {
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
                mMsgAction.postValue(
                    Pair(true, mApplication.getString(R.string.text_update_registers))
                )
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
                mMsgAction.postValue(
                    Pair(true, mApplication.getString(R.string.text_update_registers))
                )
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
    }
}