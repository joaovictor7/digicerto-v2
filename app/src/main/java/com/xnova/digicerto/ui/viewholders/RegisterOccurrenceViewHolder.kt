package com.xnova.digicerto.ui.viewholders

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.xnova.digicerto.R
import com.xnova.digicerto.databinding.RowRegisterOccurrenceBinding
import com.xnova.digicerto.models.entities.Occurrence
import com.xnova.digicerto.services.enums.settings.occurrence.OccurrenceType
import com.xnova.digicerto.services.factories.inputs.OnClickFactory
import com.xnova.digicerto.services.util.StringUtil

class RegisterOccurrenceViewHolder(
    binding: RowRegisterOccurrenceBinding,
    private val mContext: Context
) :
    RecyclerView.ViewHolder(binding.root) {

    private val mBinding = binding
    private lateinit var mOccurrence: Occurrence

    fun bind(Occurrence: Occurrence) {
        mOccurrence = Occurrence

        listeners()
        setComponents()
    }

    private fun listeners() {
        val onClickFactory = OnClickFactory(mContext)
        mBinding.root.setOnClickListener(onClickFactory.closeKeyboard(mBinding.root))
    }

    private fun setComponents() {
        mBinding.textOccurrence.text = mOccurrence.description
        mBinding.textOccurrenceCode.text = mOccurrence.code.toString()
        mBinding.textOccurrenceType.text = when (mOccurrence.type) {
            OccurrenceType.Collect -> getOccurrenceType(R.string.text_collect)
            else -> getOccurrenceType(R.string.text_not_collect)
        }
    }

    private fun getOccurrenceType(textId: Int): String {
        val type = mContext.getString(textId).lowercase()
        return mContext.getString(R.string.text_occurrence_type_colon, type)
    }
}