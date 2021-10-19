package com.xnova.digicerto.ui.viewholders

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.xnova.digicerto.databinding.RowRegisterDriverBinding
import com.xnova.digicerto.models.entities.Driver
import com.xnova.digicerto.services.factories.inputs.OnClickFactory
import com.xnova.digicerto.services.util.StringUtil

class RegisterDriverViewHolder(binding: RowRegisterDriverBinding, private val mContext: Context) :
    RecyclerView.ViewHolder(binding.root) {

    private val mBinding = binding
    private lateinit var mDriver: Driver

    fun bind(driver: Driver) {
        mDriver = driver

        listeners()
        setComponents()
    }

    private fun listeners() {
        val onClickFactory = OnClickFactory(mContext)
        mBinding.root.setOnClickListener(onClickFactory.closeKeyboard(mBinding.root))
    }

    private fun setComponents() {
        mBinding.textDriverName.text = mDriver.name
        mBinding.textDriverCode.text = mDriver.code.toString()
    }
}