package com.xnova.digicerto.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xnova.digicerto.databinding.FragmentSettingsBinding
import com.xnova.digicerto.services.adapters.SettingsAdapter

class SettingsFragment : Fragment() {

    private lateinit var mViewModel: SettingsViewModel
    private var mBinding: FragmentSettingsBinding? = null

    private fun binding() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        mBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root = binding().root

        val recyclerView = binding().recyclerSettings
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = SettingsAdapter()

        mViewModel.load()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}