package com.xnova.digicerto.ui.viewholders

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.xnova.digicerto.R
import com.xnova.digicerto.databinding.RowRegisterRouteBinding
import com.xnova.digicerto.models.entities.relations.RouteWithQuantityProducers
import com.xnova.digicerto.services.enums.settings.travel.TravelType
import com.xnova.digicerto.services.factories.inputs.OnClickFactory
import com.xnova.digicerto.services.repositories.local.entities.SettingsRepository

class RegisterRouteViewHolder(
    binding: RowRegisterRouteBinding,
    private val mContext: Context
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var mRoute: RouteWithQuantityProducers
    private val mSettings = SettingsRepository(mContext).get()
    private val mBinding = binding

    fun bind(vehicle: RouteWithQuantityProducers) {
        mRoute = vehicle

        listeners()
        setComponents()
    }

    private fun listeners() {
        val onClickFactory = OnClickFactory(mContext)
        mBinding.root.setOnClickListener(onClickFactory.closeKeyboard(mBinding.root))
    }

    private fun setComponents() {
        mBinding.textRouteName.text = mRoute.route.name
        mBinding.textRouteCode.text = mRoute.route.code.toString()

        if (mSettings.travelSettings.type == TravelType.Defined) {
            mBinding.textQuantityProducers.visibility = View.VISIBLE
            mBinding.textQuantityProducers.text =
                mContext.getString(R.string.text_route_producers_colon, mRoute.quantityProducers)
        } else {
            mBinding.textQuantityProducers.visibility = View.GONE
        }
    }
}