package com.xnova.digicerto.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.xnova.digicerto.databinding.RowRegisterBinding
import com.xnova.digicerto.models.Register
import com.xnova.digicerto.services.listeners.RegisterListener

class RegisterViewHolder(
    binding: RowRegisterBinding,
    listener: RegisterListener
) : RecyclerView.ViewHolder(binding.root) {

    private val mBinding = binding
    private val mListener = listener
    private lateinit var mRegister: Register

    fun bind(register: Register) {
        mRegister = register
        listeners()
        setComponents()
    }

    private fun listeners() {
        mBinding.constraintRegister.setOnClickListener {
            mListener.onClick(mRegister)
        }
    }

    private fun setComponents() {
        mBinding.textRegister.text = mRegister.name
        mBinding.textRegisterCount.text = mRegister.count.toString()
    }
}