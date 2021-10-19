package com.xnova.digicerto.ui.register.vehicle

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xnova.digicerto.databinding.ActivityRegisterVehicleBinding
import com.xnova.digicerto.ui.BaseActivity
import com.xnova.digicerto.ui.adapters.RegisterVehicleAdapter

class RegisterVehicleActivity : BaseActivity() {

    private lateinit var mBinding: ActivityRegisterVehicleBinding
    private lateinit var mViewModel: RegisterVehicleViewModel
    private lateinit var mRegisterVehicleAdapter: RegisterVehicleAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        mViewModel = ViewModelProvider(this).get(RegisterVehicleViewModel::class.java)
        mBinding = ActivityRegisterVehicleBinding.inflate(layoutInflater)
        viewRoot = mBinding.root
        setContentView(viewRoot)
        setCloseKeyboardOnClick()

        mRegisterVehicleAdapter = RegisterVehicleAdapter(this)

        listeners()
        observers()
        adapters()
        setComponents()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun listeners() {
        mBinding.editFindVehicle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mViewModel.filterVehicles(s.toString().trim())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun observers() {
        mViewModel.refreshScreen.observe(this, {
            setComponents()
        })
    }

    private fun adapters() {
        val recycler = mBinding.recyclerVehicles
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = mRegisterVehicleAdapter
    }

    private fun setComponents() {
        mRegisterVehicleAdapter.setVehicles(mViewModel.filteredVehicles)
    }
}