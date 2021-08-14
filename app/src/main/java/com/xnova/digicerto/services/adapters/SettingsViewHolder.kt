package com.xnova.digicerto.services.adapters

import androidx.recyclerview.widget.RecyclerView
import com.xnova.digicerto.databinding.RowSettingsBinding

class SettingsViewHolder(private val mBinding: RowSettingsBinding) : RecyclerView.ViewHolder(mBinding.root) {

    fun bind(settings: Pair<String, String>) {
        mBinding.textName.text =  settings.first
        mBinding.textDescription.text = settings.second
    }
}