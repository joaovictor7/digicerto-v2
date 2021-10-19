package com.xnova.digicerto.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xnova.digicerto.databinding.RowRegisterOccurrenceBinding
import com.xnova.digicerto.models.entities.Occurrence
import com.xnova.digicerto.ui.viewholders.RegisterOccurrenceViewHolder

class RegisterOccurrenceAdapter(private val mContext: Context) : RecyclerView.Adapter<RegisterOccurrenceViewHolder>() {

    private var mOccurrences = listOf<Occurrence>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisterOccurrenceViewHolder {
        val binding =
            RowRegisterOccurrenceBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RegisterOccurrenceViewHolder(binding, mContext)
    }

    override fun onBindViewHolder(holder: RegisterOccurrenceViewHolder, position: Int) {
        holder.bind(mOccurrences[position])
    }

    override fun getItemCount(): Int {
        return mOccurrences.count()
    }

    fun setOccurrences(list: List<Occurrence>) {
        mOccurrences = list.sortedBy { it.description }
        notifyDataSetChanged()
    }
}