package com.xnova.digicerto.services.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.xnova.digicerto.databinding.RowSettingsBinding
import com.xnova.digicerto.models.MenuSettings
import com.xnova.digicerto.services.listeners.MenuSettingsListener

class SettingsViewHolder(
    private val mBinding: RowSettingsBinding,
    private val mListener: MenuSettingsListener
) : RecyclerView.ViewHolder(mBinding.root) {

    private lateinit var mMenu: MenuSettings

    fun bind(menuSettings: MenuSettings) {
        mMenu = menuSettings
        listeners()
        setComponents()
    }

    private fun listeners() {
        constraintSettingsListener()
    }

    private fun constraintSettingsListener() {
        mBinding.constraintSettings.setOnClickListener {
            mListener.onClick(mMenu)
        }
    }

    private fun setComponents() {
        mBinding.textName.text = mMenu.title
        mBinding.textDescription.text = mMenu.description
    }
}