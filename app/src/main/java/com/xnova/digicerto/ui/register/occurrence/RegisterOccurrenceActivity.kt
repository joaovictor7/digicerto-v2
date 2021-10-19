package com.xnova.digicerto.ui.register.occurrence

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xnova.digicerto.databinding.ActivityRegisterOccurrenceBinding
import com.xnova.digicerto.services.factories.inputs.OnClickFactory
import com.xnova.digicerto.ui.BaseActivity
import com.xnova.digicerto.ui.adapters.RegisterOccurrenceAdapter

class RegisterOccurrenceActivity : BaseActivity() {

    private lateinit var mBinding: ActivityRegisterOccurrenceBinding
    private lateinit var mViewModel: RegisterOccurrenceViewModel
    private lateinit var mRegisterOccurrenceAdapter: RegisterOccurrenceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProvider(this).get(RegisterOccurrenceViewModel::class.java)
        mBinding = ActivityRegisterOccurrenceBinding.inflate(layoutInflater)
        viewRoot = mBinding.root
        setContentView(viewRoot)
        setCloseKeyboardOnClick()

        mRegisterOccurrenceAdapter = RegisterOccurrenceAdapter(this)

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
        mBinding.editFindOccurrence.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mViewModel.filterOccurrences(s.toString().trim())
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
        val recycler = mBinding.recyclerOccurrences
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = mRegisterOccurrenceAdapter
    }

    private fun setComponents() {
        mRegisterOccurrenceAdapter.setOccurrences(mViewModel.filteredOccurrences)
    }
}