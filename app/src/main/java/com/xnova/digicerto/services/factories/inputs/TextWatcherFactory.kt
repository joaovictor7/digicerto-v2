package com.xnova.digicerto.services.factories.inputs

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout

class TextWatcherFactory {

    companion object {
        fun cleanError(textInputLayout: TextInputLayout): TextWatcher {
            return object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (textInputLayout.error != null) {
                        textInputLayout.error = null
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            }
        }
    }
}