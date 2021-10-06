package com.xnova.digicerto.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.xnova.digicerto.R
import com.xnova.digicerto.databinding.FragmentRegisterBinding
import com.xnova.digicerto.ui.BaseFragment

class RegisterFragment : BaseFragment() {

    private var mBinding: FragmentRegisterBinding? = null
    private lateinit var mViewModel: RegisterViewModel

    private fun binding() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        viewRoot = binding().root

        observers()
        setComponents()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    fun observers() {
        mViewModel.refreshScreen.observe(requireActivity(), {
            setComponents()
        })
    }

    fun setComponents() {
        binding().textLastSyncValue.text = mViewModel.getLatestSync()

        if (mViewModel.settings.necessaryChooseTypeOperation) {
            binding().buttonSync.isEnabled = false
            showHelp(binding().textSyncHelp, R.string.msg_operation_type_not_configured)
        } else {
            binding().buttonSync.isEnabled = true
            hideHelp(binding().textSyncHelp)
        }
    }

}