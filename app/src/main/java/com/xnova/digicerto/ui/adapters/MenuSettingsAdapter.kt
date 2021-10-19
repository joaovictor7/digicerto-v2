package com.xnova.digicerto.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xnova.digicerto.databinding.RowMenuSettingsBinding
import com.xnova.digicerto.models.MenuSettings
import com.xnova.digicerto.services.listeners.MenuSettingsListener
import com.xnova.digicerto.ui.viewholders.MenuSettingsViewHolder

class MenuSettingsAdapter : RecyclerView.Adapter<MenuSettingsViewHolder>() {

    private lateinit var mListener: MenuSettingsListener
    private var mMenuSettings = listOf<MenuSettings>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuSettingsViewHolder {
        val binding =
            RowMenuSettingsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MenuSettingsViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holderMenu: MenuSettingsViewHolder, position: Int) {
        holderMenu.bind(mMenuSettings[position])
    }

    override fun getItemCount(): Int {
        return mMenuSettings.count()
    }

    fun setListener(listener: MenuSettingsListener) {
        mListener = listener
    }

    fun setMenuSettings(menus: List<MenuSettings>) {
        mMenuSettings = menus.sortedBy { it.title }
        notifyDataSetChanged()
    }
}