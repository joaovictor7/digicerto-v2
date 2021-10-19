package com.xnova.digicerto.ui.viewholders

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.xnova.digicerto.R
import com.xnova.digicerto.databinding.RowRegisterProducerBinding
import com.xnova.digicerto.models.entities.Producer
import com.xnova.digicerto.services.enums.producer.TankType
import com.xnova.digicerto.services.factories.inputs.OnClickFactory
import com.xnova.digicerto.services.util.StringUtil

class RegisterProducerViewHolder(
    binding: RowRegisterProducerBinding,
    private val mContext: Context
) : RecyclerView.ViewHolder(binding.root) {

    private val mBinding = binding
    private lateinit var mProducer: Producer

    fun bind(producer: Producer) {
        mProducer = producer

        listeners()
        setComponents()
    }

    private fun listeners() {
        val onClickFactory = OnClickFactory(mContext)
        mBinding.root.setOnClickListener(onClickFactory.closeKeyboard(mBinding.root))
    }

    private fun setComponents() {
        mBinding.textAvgVolume.text =
            mContext.getString(R.string.text_avg_volume_colon, mProducer.avgVolume ?: "-")
        mBinding.textProducerCode.text = mProducer.code.toString()
        mBinding.textProducerName.text = mProducer.name

        if (mProducer.ownFarm) {
            mBinding.textFarmName.visibility = View.VISIBLE
            mBinding.textFarmCode.visibility = View.VISIBLE
            mBinding.textFarmCode.text = mProducer.farmCode.toString()
            mBinding.textFarmName.text = if (mProducer.farmName != null) {
                mProducer.farmName
            } else {
                "-"
            }
        } else {
            mBinding.textFarmName.visibility = View.GONE
            mBinding.textFarmCode.visibility = View.GONE
        }

        if (mProducer.tankCode != null) {
            mBinding.textTankCode.visibility = View.VISIBLE
            mBinding.textTankCode.text =
                mContext.getString(R.string.text_tank_code_colon, mProducer.tankCode.toString())
        } else {
            mBinding.textTankCode.visibility = View.GONE
        }

        mBinding.textProducerType.text = when (mProducer.tankType) {
            TankType.Individual -> getProducerType(R.string.text_individual)
            TankType.Collective -> getProducerType(R.string.text_collective)
            else -> getProducerType(R.string.text_associate)
        }
    }

    private fun getProducerType(textId: Int): String {
        val type = mContext.getString(textId).lowercase()
        return mContext.getString(R.string.text_producer_type_colon, type)
    }
}