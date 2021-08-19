package com.xnova.digicerto.services.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xnova.digicerto.databinding.RowSettingsBinding
import com.xnova.digicerto.models.MenuSettings
import com.xnova.digicerto.services.listeners.MenuSettingsListener
import com.xnova.digicerto.services.viewholders.SettingsViewHolder

class SettingsAdapter : RecyclerView.Adapter<SettingsViewHolder>() {

    private lateinit var mListener: MenuSettingsListener
    private var mSettings: List<MenuSettings> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val binding = RowSettingsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SettingsViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.bind(mSettings[position])
    }

    override fun getItemCount(): Int {
        return mSettings.count()
    }

    fun onAttach(listener: MenuSettingsListener) {
        mListener = listener
    }

    fun updateSettings(list: List<MenuSettings>) {
        mSettings = list
        //notifyDataSetChanged()
    }
}