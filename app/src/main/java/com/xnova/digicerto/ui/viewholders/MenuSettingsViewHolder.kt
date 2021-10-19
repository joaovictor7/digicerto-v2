package com.xnova.digicerto.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.xnova.digicerto.databinding.RowMenuSettingsBinding
import com.xnova.digicerto.models.MenuSettings
import com.xnova.digicerto.services.listeners.MenuSettingsListener

class MenuSettingsViewHolder(
    binding: RowMenuSettingsBinding,
    listener: MenuSettingsListener
) : RecyclerView.ViewHolder(binding.root) {

    private val mBinding = binding
    private val mListener = listener
    private lateinit var mMenuSettings: MenuSettings

    fun bind(menuSettings: MenuSettings) {
        mMenuSettings = menuSettings
        listeners()
        setComponents()
    }

    private fun listeners() {
        mBinding.linearSettings.setOnClickListener {
            mListener.onClick(mMenuSettings)
        }
    }

    private fun setComponents() {
        mBinding.textSettings.text = mMenuSettings.title
        mBinding.textDescription.text = mMenuSettings.description
    }
}