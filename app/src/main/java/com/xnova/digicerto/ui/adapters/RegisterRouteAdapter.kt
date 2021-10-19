package com.xnova.digicerto.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xnova.digicerto.databinding.RowRegisterRouteBinding
import com.xnova.digicerto.models.entities.relations.RouteWithQuantityProducers
import com.xnova.digicerto.ui.viewholders.RegisterRouteViewHolder

class RegisterRouteAdapter(context: Context) : RecyclerView.Adapter<RegisterRouteViewHolder>() {

    private val mContext = context
    private var mRoutes = listOf<RouteWithQuantityProducers>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisterRouteViewHolder {
        val binding =
            RowRegisterRouteBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RegisterRouteViewHolder(binding, mContext)
    }

    override fun onBindViewHolder(holder: RegisterRouteViewHolder, position: Int) {
        holder.bind(mRoutes[position])
    }

    override fun getItemCount(): Int {
        return mRoutes.count()
    }

    fun setRoutes(routes: List<RouteWithQuantityProducers>) {
        mRoutes = routes.sortedBy { it.route.name }
        notifyDataSetChanged()
    }
}