package com.xnova.digicerto.ui.settings

import com.xnova.digicerto.databinding.ActivityTravelSettingsBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.xnova.digicerto.R

class TravelSettingsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityTravelSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityTravelSettingsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        actionBar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun actionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.text_travel)
    }
}