package com.xnova.digicerto.ui.register.producer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xnova.digicerto.databinding.ActivityRegisterProducerBinding
import com.xnova.digicerto.ui.BaseActivity
import com.xnova.digicerto.ui.adapters.RegisterProducerAdapter

class RegisterProducerActivity : BaseActivity() {

    private lateinit var mBinding: ActivityRegisterProducerBinding
    private lateinit var mViewModel: RegisterProducerViewModel
    private lateinit var mRegisterProducerAdapter: RegisterProducerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProvider(this).get(RegisterProducerViewModel::class.java)
        mBinding = ActivityRegisterProducerBinding.inflate(layoutInflater)
        viewRoot = mBinding.root
        setContentView(viewRoot)
        setCloseKeyboardOnClick()

        mRegisterProducerAdapter = RegisterProducerAdapter(this)

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
        mBinding.editFindProducer.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mViewModel.filterProducers(s.toString().trim())
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
        val recycler = mBinding.recyclerProducers
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = mRegisterProducerAdapter
    }

    private fun setComponents() {
        mRegisterProducerAdapter.setProducers(mViewModel.filteredProducers)
    }
}