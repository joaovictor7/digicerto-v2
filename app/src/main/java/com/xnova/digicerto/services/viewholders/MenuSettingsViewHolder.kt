package com.xnova.digicerto.services.viewholders

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