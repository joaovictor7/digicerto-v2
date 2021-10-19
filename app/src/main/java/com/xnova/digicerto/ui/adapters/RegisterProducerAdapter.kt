package com.xnova.digicerto.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xnova.digicerto.databinding.RowRegisterProducerBinding
import com.xnova.digicerto.models.entities.Producer
import com.xnova.digicerto.ui.viewholders.RegisterProducerViewHolder

class RegisterProducerAdapter(context: Context) : RecyclerView.Adapter<RegisterProducerViewHolder>() {

    private val mContext = context
    private var mProducers = listOf<Producer>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisterProducerViewHolder {
        val binding =
            RowRegisterProducerBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RegisterProducerViewHolder(binding, mContext)
    }

    override fun onBindViewHolder(holder: RegisterProducerViewHolder, position: Int) {
        holder.bind(mProducers[position])
    }

    override fun getItemCount(): Int {
        return mProducers.count()
    }

    fun setProducers(list: List<Producer>) {
        mProducers = list.sortedBy { it.name }
        notifyDataSetChanged()
    }
}