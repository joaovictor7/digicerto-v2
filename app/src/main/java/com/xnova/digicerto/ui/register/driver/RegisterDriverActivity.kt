package com.xnova.digicerto.ui.register.driver

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xnova.digicerto.databinding.ActivityRegisterDriverBinding
import com.xnova.digicerto.services.factories.inputs.OnClickFactory
import com.xnova.digicerto.ui.BaseActivity
import com.xnova.digicerto.ui.adapters.RegisterDriverAdapter

class RegisterDriverActivity : BaseActivity() {

    private lateinit var mBinding: ActivityRegisterDriverBinding
    private lateinit var mViewModel: RegisterDriverViewModel
    private lateinit var mRegisterDriverAdapter: RegisterDriverAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProvider(this).get(RegisterDriverViewModel::class.java)
        mBinding = ActivityRegisterDriverBinding.inflate(layoutInflater)
        viewRoot = mBinding.root
        setContentView(viewRoot)
        setCloseKeyboardOnClick()

        mRegisterDriverAdapter = RegisterDriverAdapter(this)

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
        mBinding.editFindDriver.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mViewModel.filterDrivers(s.toString().trim())
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
        val recycler = mBinding.recyclerDrivers
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = mRegisterDriverAdapter
    }

    private fun setComponents() {
        mRegisterDriverAdapter.setDrivers(mViewModel.filteredDrivers)
    }
}