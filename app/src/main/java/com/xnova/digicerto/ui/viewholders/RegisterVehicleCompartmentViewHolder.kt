package com.xnova.digicerto.ui.viewholders

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.xnova.digicerto.databinding.RowRegisterVehicleCompartmentBinding
import com.xnova.digicerto.models.entities.VehicleCompartment
import com.xnova.digicerto.services.factories.inputs.OnClickFactory

class RegisterVehicleCompartmentViewHolder(
    private val mBinding: RowRegisterVehicleCompartmentBinding,
    private val mContext: Context
) : RecyclerView.ViewHolder(mBinding.root) {

    private lateinit var mVehicleCompartment: VehicleCompartment

    fun bind(vehicleCompartment: VehicleCompartment) {
        mVehicleCompartment = vehicleCompartment

        listeners()
        setComponents()
    }

    private fun listeners() {
        val onClickFactory = OnClickFactory(mContext)
        mBinding.root.setOnClickListener(onClickFactory.closeKeyboard(mBinding.root))
    }

    private fun setComponents() {
        mBinding.textCompartment.text = mVehicleCompartment.compartment.toString()
        mBinding.textCapacity.text = mVehicleCompartment.capacity.toString()
    }
}