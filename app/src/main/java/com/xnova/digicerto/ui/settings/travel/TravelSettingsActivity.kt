package com.xnova.digicerto.ui.settings.travel

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.xnova.digicerto.R
import com.xnova.digicerto.databinding.ActivityTravelSettingsBinding
import com.xnova.digicerto.models.entities.settings.TravelSettings
import com.xnova.digicerto.services.enums.settings.travel.*
import com.xnova.digicerto.ui.BaseActivity

class TravelSettingsActivity : BaseActivity(R.string.text_travel) {

    private lateinit var mBinding: ActivityTravelSettingsBinding
    private lateinit var mViewModel: TravelSettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityTravelSettingsBinding.inflate(layoutInflater)
        mViewModel = ViewModelProvider(this).get(TravelSettingsViewModel::class.java)
        viewRoot = mBinding.root
        setContentView(viewRoot)

        observers()
        setComponents()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.menu_save -> {
                getComponents()
                mViewModel.save()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observers() {
        mViewModel.refreshScreen.observe(this, {
            setComponents()
        })

        mViewModel.saveMsg.observe(this, {
            showSnackBar(it)
        })
    }

    private fun setComponents() {
        mBinding.switchAwaitDischarge.isChecked = mViewModel.travelSettings.awaitDischarge
        mBinding.switchTranshipment.isChecked = mViewModel.travelSettings.transhipment
        mBinding.switchProducerCodeReading.isChecked = mViewModel.travelSettings.producerCodeReading
        mBinding.switchSelectWildcardProducer.isChecked = mViewModel.travelSettings.wildcardProducer

        when (mViewModel.travelSettings.type) {
            TravelType.Defined -> mBinding.radioTravelDefined.isChecked = true
            TravelType.Undefined -> mBinding.radioTravelUndefined.isChecked = true
        }

        when (mViewModel.travelSettings.milkDensity) {
            MilkDensity.Default -> mBinding.radioMilkDensityDefault.isChecked = true
            MilkDensity.Measure -> mBinding.radioMilkDensityMeasured.isChecked = true
        }

        when (mViewModel.travelSettings.platformMeasure) {
            PlatformMeasure.None -> mBinding.radioPlatformNone.isChecked = true
            PlatformMeasure.Volume -> mBinding.radioPlatformVolume.isChecked = true
            PlatformMeasure.Weight -> mBinding.radioPlatformWeight.isChecked = true
        }

        when (mViewModel.travelSettings.travelCodeReading) {
            TravelCodeReading.None -> mBinding.radioTravelCodeNone.isChecked = true
            TravelCodeReading.Manual -> mBinding.radioTravelCodeManual.isChecked = true
            TravelCodeReading.ReadBarcode -> mBinding.radioTravelCodeReadBarcode.isChecked = true
        }

        when (mViewModel.travelSettings.odometerRegister) {
            OdometerRegister.None -> mBinding.radioOdometerNone.isChecked = true
            OdometerRegister.Photo -> mBinding.radioOdometerPhoto.isChecked = true
            OdometerRegister.Typed -> mBinding.radioOdometerTyped.isChecked = true
            OdometerRegister.TypedPhoto -> mBinding.radioOdometerTypedPhoto.isChecked = true
        }
    }

    private fun getComponents() {
        val awaitDischarge = mBinding.switchAwaitDischarge.isChecked
        val producerCodeReading = mBinding.switchProducerCodeReading.isChecked
        val transhipment = mBinding.switchTranshipment.isChecked
        val selectWildcardProducer = mBinding.switchSelectWildcardProducer.isChecked
        val type = when {
            mBinding.radioTravelDefined.isChecked -> TravelType.Defined
            else -> TravelType.Undefined
        }
        val milkDensity = when {
            mBinding.radioMilkDensityDefault.isChecked -> MilkDensity.Default
            else -> MilkDensity.Measure
        }
        val platformMeasure = when {
            mBinding.radioPlatformNone.isChecked -> PlatformMeasure.None
            mBinding.radioPlatformVolume.isChecked -> PlatformMeasure.Volume
            else -> PlatformMeasure.Weight
        }
        val travelCodeReading = when {
            mBinding.radioTravelCodeReadBarcode.isChecked -> TravelCodeReading.ReadBarcode
            mBinding.radioTravelCodeNone.isChecked -> TravelCodeReading.None
            else -> TravelCodeReading.Manual
        }
        val odometerRegister = when {
            mBinding.radioOdometerNone.isChecked -> OdometerRegister.None
            mBinding.radioOdometerPhoto.isChecked -> OdometerRegister.Photo
            mBinding.radioOdometerTyped.isChecked -> OdometerRegister.Typed
            else -> OdometerRegister.TypedPhoto
        }

        val travelSettings = TravelSettings(
            type,
            producerCodeReading,
            selectWildcardProducer,
            transhipment,
            travelCodeReading,
            platformMeasure,
            milkDensity,
            awaitDischarge,
            odometerRegister
        )
        mViewModel.travelSettings = travelSettings
    }
}