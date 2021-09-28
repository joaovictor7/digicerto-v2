package com.xnova.digicerto.ui.settings.collect

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.xnova.digicerto.R
import com.xnova.digicerto.databinding.ActivityCollectSettingsBinding
import com.xnova.digicerto.models.entities.settings.CollectSettings
import com.xnova.digicerto.services.enums.settings.collect.CollectiveTankCollection
import com.xnova.digicerto.services.enums.settings.collect.RegisterSample
import com.xnova.digicerto.services.factories.inputs.OnClickFactory
import com.xnova.digicerto.services.factories.inputs.TextWatcherFactory

class CollectSettingsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityCollectSettingsBinding
    private lateinit var mViewModel: CollectSettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProvider(this).get(CollectSettingsViewModel::class.java)
        mBinding = ActivityCollectSettingsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        actionBar()
        listeners()
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
                if (componentsValidate()) {
                    getComponents()
                    mViewModel.save()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun actionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.text_collect)
    }

    private fun listeners() {
        mBinding.editProductionTolerancePercentage.addTextChangedListener(
            TextWatcherFactory.cleanError(mBinding.inputProductionTolerancePercentage)
        )

        val onClickFactory = OnClickFactory(this)
        mBinding.root.getChildAt(0)
            .setOnClickListener(onClickFactory.closeKeyboard(mBinding.root.getChildAt(0)))
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
        val percentage = mViewModel.collectSettings.productionTolerancePercentage?.toString()
        mBinding.editProductionTolerancePercentage.setText(percentage)

        mBinding.switchAcidMilk.isChecked = mViewModel.collectSettings.acidMilk
        mBinding.switchTemperaturePicture.isChecked =
            mViewModel.collectSettings.temperaturePicture

        when (mViewModel.collectSettings.collectiveTankCollection) {
            CollectiveTankCollection.Typed -> mBinding.radioCollectiveTankTyped.isChecked = true
            CollectiveTankCollection.Photo -> mBinding.radioCollectiveTankPhoto.isChecked = true
            CollectiveTankCollection.TypedPhoto -> mBinding.radioCollectiveTankTypedPhoto.isChecked =
                true
        }

        when (mViewModel.collectSettings.registerSample) {
            RegisterSample.AleatoryCode -> mBinding.radioSampleAleatoryCode.isChecked = true
            RegisterSample.None -> mBinding.radioSampleNone.isChecked = true
            RegisterSample.ProducerCode -> mBinding.radioSampleProducerCode.isChecked = true
            RegisterSample.ReadBarcode -> mBinding.radioSampleReadBarcode.isChecked = true
        }
    }

    private fun getComponents() {
        val productionTolerancePercentage =
            mBinding.editProductionTolerancePercentage.text.toString().toDoubleOrNull()
        val acidMilk = mBinding.switchAcidMilk.isChecked
        val temperaturePicture = mBinding.switchTemperaturePicture.isChecked
        val type = when {
            mBinding.radioCollectiveTankPhoto.isChecked -> CollectiveTankCollection.Photo
            mBinding.radioCollectiveTankTyped.isChecked -> CollectiveTankCollection.Typed
            else -> CollectiveTankCollection.TypedPhoto
        }
        val registerSample = when {
            mBinding.radioSampleAleatoryCode.isChecked -> RegisterSample.AleatoryCode
            mBinding.radioSampleNone.isChecked -> RegisterSample.None
            mBinding.radioSampleProducerCode.isChecked -> RegisterSample.ProducerCode
            else -> RegisterSample.ReadBarcode
        }

        val collectSettings = CollectSettings(
            type,
            registerSample,
            acidMilk,
            temperaturePicture,
            productionTolerancePercentage
        )
        mViewModel.collectSettings = collectSettings
    }

    private fun showSnackBar(messageId: Int) {
        Snackbar.make(mBinding.root, messageId, Snackbar.LENGTH_LONG).show()
    }

    private fun componentsValidate(): Boolean {
        var valid = true

        val percentage = mBinding.editProductionTolerancePercentage.text
        percentage.toString().toDoubleOrNull()?.let {
            if (it <= 0) {
                valid = false
                mBinding.inputProductionTolerancePercentage.error =
                    getString(R.string.msg_value_greater_zero)
            } else if (it > 100) {
                valid = false
                mBinding.inputProductionTolerancePercentage.error =
                    getString(R.string.msg_value_greater_hundred)
            }
        }

        return valid
    }
}