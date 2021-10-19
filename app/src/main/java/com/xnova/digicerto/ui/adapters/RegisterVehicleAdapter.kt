package com.xnova.digicerto.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xnova.digicerto.databinding.RowRegisterVehicleBinding
import com.xnova.digicerto.models.entities.relations.VehicleWithCompartments
import com.xnova.digicerto.ui.viewholders.RegisterVehicleViewHolder

class RegisterVehicleAdapter(context: Context) : RecyclerView.Adapter<RegisterVehicleViewHolder>() {

    private val mContext = context
    private var mVehicles = listOf<VehicleWithCompartments>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisterVehicleViewHolder {
        val binding =
            RowRegisterVehicleBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RegisterVehicleViewHolder(binding, mContext)
    }

    override fun onBindViewHolder(holder: RegisterVehicleViewHolder, position: Int) {
        holder.bind(mVehicles[position])
    }

    override fun getItemCount(): Int {
        return mVehicles.count()
    }

    fun setVehicles(vehicles: List<VehicleWithCompartments>) {
        mVehicles = vehicles.sortedBy { it.vehicle.code }
        notifyDataSetChanged()
    }
}