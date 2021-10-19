package com.xnova.digicerto.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xnova.digicerto.databinding.RowRegisterDriverBinding
import com.xnova.digicerto.models.entities.Driver
import com.xnova.digicerto.ui.viewholders.RegisterDriverViewHolder

class RegisterDriverAdapter(context: Context) : RecyclerView.Adapter<RegisterDriverViewHolder>() {

    private val mContext = context
    private var mDrivers = listOf<Driver>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisterDriverViewHolder {
        val binding =
            RowRegisterDriverBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RegisterDriverViewHolder(binding, mContext)
    }

    override fun onBindViewHolder(holder: RegisterDriverViewHolder, position: Int) {
        holder.bind(mDrivers[position])
    }

    override fun getItemCount(): Int {
        return mDrivers.count()
    }

    fun setDrivers(list: List<Driver>) {
        mDrivers = list.sortedBy { it.name }
        notifyDataSetChanged()
    }
}