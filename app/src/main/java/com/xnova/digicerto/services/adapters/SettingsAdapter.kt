package com.xnova.digicerto.services.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xnova.digicerto.databinding.RowSettingsBinding

class SettingsAdapter : RecyclerView.Adapter<SettingsViewHolder>() {

    private var mSettings: List<Pair<String,String>> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val binding = RowSettingsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SettingsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.bind(mSettings[position])
    }

    override fun getItemCount(): Int {
        return mSettings.count()
    }

    fun updateSettings(list: List<Pair<String,String>>){
        mSettings = list
        notifyDataSetChanged()
    }
}