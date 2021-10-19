package com.xnova.digicerto.ui.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xnova.digicerto.R
import com.xnova.digicerto.models.Alert
import com.xnova.digicerto.models.Register
import com.xnova.digicerto.services.constants.RegisterConstants
import com.xnova.digicerto.services.enums.AlertType
import com.xnova.digicerto.services.enums.settings.OperationType
import com.xnova.digicerto.services.repositories.local.entities.*
import com.xnova.digicerto.services.sync.SyncFTPService
import com.xnova.digicerto.services.sync.SyncService
import com.xnova.digicerto.services.util.NetworkUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.ocpsoft.prettytime.PrettyTime

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val mApplication = application
    private val mSettingsRepository = SettingsRepository(application)
    private val mPrettyTime = PrettyTime()
    private val mProducerRepository = ProducerRepository(mApplication)
    private val mRouteRepository = RouteRepository(mApplication)
    private val mVehicleRepository = VehicleRepository(mApplication)
    private val mDriverRepository = DriverRepository(mApplication)
    private val mOccurrenceRepository = OccurrenceRepository(mApplication)
    private lateinit var mSyncService: SyncService

    var settings = mSettingsRepository.get()
        private set

    private val mRefreshScreen = MutableLiveData<Boolean>()
    val refreshScreen: LiveData<Boolean> = mRefreshScreen

    private val mShowAlert = MutableLiveData<Alert>()
    val showAlert: LiveData<Alert> = mShowAlert

    private val mShowProgressBar = MutableLiveData<Boolean>()
    val showProgressBar: LiveData<Boolean> = mShowProgressBar

    fun sync() {
        if (!syncValidations()) {
            return
        }

        showProgressBar(true)
        syncData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ }, {
                showProgressBar(false)
                mShowAlert.value =
                    Alert(AlertType.Error, R.string.text_failure, R.string.msg_sync_failure)
            }, {
                showProgressBar(false)
                refreshEntities()
                mShowAlert.value =
                    Alert(AlertType.Success, R.string.text_all_right, R.string.msg_sync_success)
            })
    }

    fun refreshEntities() {
        settings = mSettingsRepository.get()
        mRefreshScreen.value = true
    }

    fun getLatestSync(): String {
        return settings.latestSync?.let {
            mPrettyTime.format(settings.latestSync)
        } ?: mApplication.getString(R.string.msg_not_sync).lowercase()
    }

    fun getRegisters(): List<Register> {
        return listOf(
            Register(
                RegisterConstants.PRODUCER_ID,
                mApplication.getString(R.string.text_producers),
                mProducerRepository.getTotalActive()
            ),
            Register(
                RegisterConstants.VEHICLE_ID,
                mApplication.getString(R.string.text_vehicles),
                mVehicleRepository.getTotalActives()
            ),
            Register(
                RegisterConstants.DRIVER_ID,
                mApplication.getString(R.string.text_drivers),
                mDriverRepository.getTotalActives()
            ),
            Register(
                RegisterConstants.OCCURRENCE_ID,
                mApplication.getString(R.string.text_occurrences),
                mOccurrenceRepository.getTotalActives()
            ),
            Register(
                RegisterConstants.ROUTE_ID,
                mApplication.getString(R.string.text_routes),
                mRouteRepository.getTotalActives()
            )
        )
    }

    private fun syncValidations(): Boolean {
        if (!NetworkUtil.isOnline(mApplication)) {
            mShowAlert.value =
                Alert(AlertType.NetworkOff, R.string.text_off_line, R.string.msg_network_connect)
            return false
        }

        if (settings.operationType == OperationType.FTP) {
            if (!settings.ftpOperationAvailable) {
                mShowAlert.value =
                    Alert(AlertType.Info, R.string.text_necessary_action, R.string.msg_register_ftp)
                return false
            }
        } else {
            return false
        }

        return true
    }

    private fun syncData(): Observable<Boolean> {
        return if (settings.operationType == OperationType.FTP) {
            syncDataFTP()
        } else {
            syncDataWS()
        }
    }

    private fun syncDataFTP(): Observable<Boolean> {
        mSyncService = SyncFTPService(mApplication)
        return mSyncService.retrieveData()
            .flatMap { mSyncService.syncProducers() }
            .flatMap { mSyncService.syncRoutes() }
            .flatMap { mSyncService.syncVehicles() }
            .flatMap { mSyncService.syncDrivers() }
            .flatMap { mSyncService.syncOccurrences() }
            .flatMap { mSyncService.updateSyncData() }
    }

    private fun syncDataWS(): Observable<Boolean> {
        //mSyncService = SyncWSService(getApplication())
        mSyncService = SyncFTPService(getApplication())
        return mSyncService.necessarySync()
            .flatMap {
                /*mShowAlert.postValue(
                    Pair(true, mApplication.getString(R.string.text_update_registers))
                )*/
                mSyncService.retrieveData()
            }
            .flatMap { mSyncService.syncProducers() }
            .flatMap { mSyncService.syncRoutes() }
            .flatMap { mSyncService.syncVehicles() }
            .flatMap { mSyncService.syncDrivers() }
            .flatMap { mSyncService.syncOccurrences() }
            .flatMap { mSyncService.updateSyncData() }
    }

    private fun showProgressBar(show: Boolean) {
        mShowProgressBar.value = show
    }
}