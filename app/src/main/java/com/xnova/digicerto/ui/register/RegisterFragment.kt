package com.xnova.digicerto.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xnova.digicerto.R
import com.xnova.digicerto.databinding.FragmentRegisterBinding
import com.xnova.digicerto.models.Register
import com.xnova.digicerto.services.constants.RegisterConstants
import com.xnova.digicerto.services.listeners.RegisterListener
import com.xnova.digicerto.ui.BaseFragment
import com.xnova.digicerto.ui.adapters.RegisterAdapter
import com.xnova.digicerto.ui.register.driver.RegisterDriverActivity
import com.xnova.digicerto.ui.register.occurrence.RegisterOccurrenceActivity
import com.xnova.digicerto.ui.register.producer.RegisterProducerActivity
import com.xnova.digicerto.ui.register.route.RegisterRouteActivity
import com.xnova.digicerto.ui.register.vehicle.RegisterVehicleActivity

class RegisterFragment : BaseFragment() {

    private val mRegisterAdapter = RegisterAdapter()
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

        listeners()
        observers()
        adapters()
        setComponents()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    private fun listeners() {
        binding().buttonSync.setOnClickListener {
            mViewModel.sync()
        }

        mRegisterAdapter.setListener(object : RegisterListener {
            override fun onClick(register: Register) {
                when (register.id) {
                    RegisterConstants.PRODUCER_ID -> openRegister(
                        RegisterConstants.PRODUCER_ID,
                        R.string.text_producers,
                        RegisterProducerActivity::class.java
                    )
                    RegisterConstants.ROUTE_ID -> openRegister(
                        RegisterConstants.ROUTE_ID,
                        R.string.text_routes,
                        RegisterRouteActivity::class.java
                    )
                    RegisterConstants.OCCURRENCE_ID -> openRegister(
                        RegisterConstants.OCCURRENCE_ID,
                        R.string.text_occurrences,
                        RegisterOccurrenceActivity::class.java
                    )
                    RegisterConstants.DRIVER_ID -> openRegister(
                        RegisterConstants.DRIVER_ID,
                        R.string.text_drivers,
                        RegisterDriverActivity::class.java
                    )
                    RegisterConstants.VEHICLE_ID -> openRegister(
                        RegisterConstants.VEHICLE_ID,
                        R.string.text_vehicles,
                        RegisterVehicleActivity::class.java
                    )
                }
            }
        })
    }

    private fun observers() {
        mViewModel.refreshScreen.observe(requireActivity(), {
            setComponents()
        })

        mViewModel.showProgressBar.observe(requireActivity(), {
            if (it) {
                showProgressBar(R.string.msg_update_registers)
            } else {
                hideProgressBar()
            }
        })

        mViewModel.showAlert.observe(requireActivity(), {
            showAlert(it.alertType, it.titleId, it.messageId)
        })
    }

    private fun adapters() {
        val recycler = binding().recyclerRegisters
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mRegisterAdapter
    }

    private fun setComponents() {
        mRegisterAdapter.setRegisters(mViewModel.getRegisters())
        binding().textLastSyncValue.text = mViewModel.getLatestSync()

        if (mViewModel.settings.necessaryChooseTypeOperation) {
            binding().buttonSync.isEnabled = false
            showHelp(binding().textSyncHelp, R.string.msg_operation_type_not_configured)
        } else {
            binding().buttonSync.isEnabled = true
            hideHelp(binding().textSyncHelp)
        }
    }

    private fun openRegister(id: Int, textRegisterId: Int, classe: Class<*>) {
        if (mRegisterAdapter.getRegisterAmount(id) > 0) {
            openActivity(classe)
        } else {
            val register = getString(textRegisterId).lowercase()
            showSnackBar(getString(R.string.msg_no_registration_registers, register))
        }
    }
}