package com.xnova.digicerto.services.factories.inputs

import android.view.View
import android.widget.AdapterView

class OnItemSelectedFactory {

    companion object {
        fun cleanError(adapterView: View): AdapterView.OnItemSelectedListener {
            return object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    adapterView.visibility = View.INVISIBLE
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }
}