package com.xnova.digicerto.ui.viewholders

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xnova.digicerto.databinding.RowRegisterVehicleBinding
import com.xnova.digicerto.models.entities.relations.VehicleWithCompartments
import com.xnova.digicerto.services.factories.inputs.OnClickFactory
import com.xnova.digicerto.services.util.StringUtil
import com.xnova.digicerto.ui.adapters.RegisterVehicleCompartmentAdapter

class RegisterVehicleViewHolder(
    binding: RowRegisterVehicleBinding,
    private val mContext: Context
) : RecyclerView.ViewHolder(binding.root) {

    private val mBinding = binding
    private lateinit var mVehicle: VehicleWithCompartments
    private val mVehicleCompartmentAdapter = RegisterVehicleCompartmentAdapter(mContext)

    fun bind(vehicle: VehicleWithCompartments) {
        mVehicle = vehicle

        listeners()
        adapters()
        setComponents()
    }

    private fun listeners() {
        val onClickFactory = OnClickFactory(mContext)
        mBinding.root.setOnClickListener(onClickFactory.closeKeyboard(mBinding.root))
    }

    private fun adapters() {
        val recycler = mBinding.recyclerCompartments
        recycler.layoutManager = LinearLayoutManager(mContext)
        recycler.adapter = mVehicleCompartmentAdapter
    }

    private fun setComponents() {
        mBinding.textVehiclePlate.text = mVehicle.vehicle.plate
        mBinding.textVehicleCode.text = mVehicle.vehicle.code.toString()
        mVehicleCompartmentAdapter.setVehicleCompartments(mVehicle.compartments)
    }
}