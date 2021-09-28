package com.xnova.digicerto.services.factories.inputs

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class OnClickFactory(context: Context) {

    private val mContext = context

    fun closeKeyboard(view: View): View.OnClickListener {
        return View.OnClickListener {
            val input =
                mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            input.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}