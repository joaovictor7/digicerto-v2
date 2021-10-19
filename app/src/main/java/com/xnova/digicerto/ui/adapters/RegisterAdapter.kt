package com.xnova.digicerto.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xnova.digicerto.databinding.RowRegisterBinding
import com.xnova.digicerto.models.Register
import com.xnova.digicerto.services.listeners.RegisterListener
import com.xnova.digicerto.ui.viewholders.RegisterViewHolder

class RegisterAdapter : RecyclerView.Adapter<RegisterViewHolder>() {

    private lateinit var mListener: RegisterListener
    private var mRegisters = listOf<Register>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisterViewHolder {
        val binding =
            RowRegisterBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RegisterViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holderMenu: RegisterViewHolder, position: Int) {
        holderMenu.bind(mRegisters[position])
    }

    override fun getItemCount(): Int {
        return mRegisters.count()
    }

    fun setListener(listener: RegisterListener) {
        mListener = listener
    }

    fun setRegisters(list: List<Register>) {
        mRegisters = list.sortedBy { it.name }
        notifyDataSetChanged()
    }

    fun getRegisterAmount(id: Int): Int {
        return mRegisters.find { it.id == id }?.count ?: 0
    }
}