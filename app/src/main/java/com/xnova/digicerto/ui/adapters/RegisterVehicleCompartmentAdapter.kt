package com.xnova.digicerto.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xnova.digicerto.databinding.RowRegisterVehicleCompartmentBinding
import com.xnova.digicerto.models.entities.VehicleCompartment
import com.xnova.digicerto.ui.viewholders.RegisterVehicleCompartmentViewHolder

class RegisterVehicleCompartmentAdapter(private val mContext: Context) :
    RecyclerView.Adapter<RegisterVehicleCompartmentViewHolder>() {

    private var mVehicleCompartments = listOf<VehicleCompartment>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RegisterVehicleCompartmentViewHolder {
        val binding = RowRegisterVehicleCompartmentBinding.inflate(
            LayoutInflater.from(mContext), parent, false
        )
        return RegisterVehicleCompartmentViewHolder(binding, mContext)
    }

    override fun onBindViewHolder(holder: RegisterVehicleCompartmentViewHolder, position: Int) {
        holder.bind(mVehicleCompartments[position])
    }

    override fun getItemCount(): Int {
        return mVehicleCompartments.count()
    }

    fun setVehicleCompartments(vehicleCompartments: List<VehicleCompartment>) {
        mVehicleCompartments = vehicleCompartments.sortedBy { it.compartment }
        notifyDataSetChanged()
    }
}