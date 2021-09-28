package com.xnova.digicerto.services.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xnova.digicerto.databinding.RowMenuSettingsBinding
import com.xnova.digicerto.models.MenuSettings
import com.xnova.digicerto.services.listeners.MenuSettingsListener
import com.xnova.digicerto.services.viewholders.MenuSettingsViewHolder

class MenuSettingsAdapter : RecyclerView.Adapter<MenuSettingsViewHolder>() {

    private lateinit var mListener: MenuSettingsListener
    private var mSettings: List<MenuSettings> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuSettingsViewHolder {
        val binding =
            RowMenuSettingsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MenuSettingsViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holderMenu: MenuSettingsViewHolder, position: Int) {
        holderMenu.bind(mSettings[position])
    }

    override fun getItemCount(): Int {
        return mSettings.count()
    }

    fun setListener(listener: MenuSettingsListener) {
        mListener = listener
    }

    fun setMenuSettings(list: List<MenuSettings>) {
        mSettings = list.sortedBy { it.title }
        notifyDataSetChanged()
    }
}