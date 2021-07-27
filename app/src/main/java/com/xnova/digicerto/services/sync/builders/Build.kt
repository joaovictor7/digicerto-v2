package com.xnova.digicerto.services.sync.builders

import android.content.Context
import android.nfc.FormatException
import com.xnova.digicerto.R

abstract class Build(private val mContext: Context, private val mRegisterType: String) {

    private val mErrorMsg = mContext.getString(R.string.msg_error_file_value_unexpected)

    abstract fun validate(line: List<String>): Boolean
    abstract fun build(line: List<String>): Any

    protected fun formatException(): Boolean {
        throw FormatException(mErrorMsg.format(mRegisterType))
    }
}