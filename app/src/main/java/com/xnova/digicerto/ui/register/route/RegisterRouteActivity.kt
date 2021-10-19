package com.xnova.digicerto.ui.register.route

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xnova.digicerto.databinding.ActivityRegisterRouteBinding
import com.xnova.digicerto.ui.BaseActivity
import com.xnova.digicerto.ui.adapters.RegisterRouteAdapter

class RegisterRouteActivity : BaseActivity() {

    private lateinit var mBinding: ActivityRegisterRouteBinding
    private lateinit var mViewModel: RegisterRouteViewModel
    private lateinit var mRegisterRouteAdapter: RegisterRouteAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        mViewModel = ViewModelProvider(this).get(RegisterRouteViewModel::class.java)
        mBinding = ActivityRegisterRouteBinding.inflate(layoutInflater)
        viewRoot = mBinding.root
        setContentView(viewRoot)
        setCloseKeyboardOnClick()

        mRegisterRouteAdapter = RegisterRouteAdapter(this)

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
        mBinding.editFindRoute.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mViewModel.filterRoutes(s.toString().trim())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun observers() {
        mViewModel.refreshScreen.observe(this) {
            setComponents()
        }
    }

    private fun adapters() {
        val recycler = mBinding.recyclerRoutes
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = mRegisterRouteAdapter
    }

    private fun setComponents() {
        mRegisterRouteAdapter.setRoutes(mViewModel.filteredRoutes)
    }
}