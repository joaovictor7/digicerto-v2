package com.xnova.digicerto.ui.settings.file

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.xnova.digicerto.R
import com.xnova.digicerto.databinding.ActivitySettingsFileBinding
import com.xnova.digicerto.models.entities.settings.FileSettings
import com.xnova.digicerto.ui.BaseActivity

class SettingsFileActivity : BaseActivity() {

    private lateinit var mBinding: ActivitySettingsFileBinding
    private lateinit var mViewModel: SettingsFileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProvider(this).get(SettingsFileViewModel::class.java)
        mBinding = ActivitySettingsFileBinding.inflate(layoutInflater)
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
            showSnackBar(getString(it))
        })
    }

    private fun setComponents() {
        mBinding.switchCollectCancelled.isChecked = mViewModel.fileSettings.collectCancelled
    }

    private fun getComponents() {
        val collectCancelled = mBinding.switchCollectCancelled.isChecked

        val fileSettings = FileSettings(collectCancelled)
        mViewModel.fileSettings = fileSettings
    }
}