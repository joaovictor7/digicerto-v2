package com.xnova.digicerto.services.repositories.local

import android.content.Context
import com.xnova.digicerto.R
import org.apache.commons.lang3.StringUtils

class PrinterRepository(context: Context) {

    companion object {
        private const val EMPTY_LINE = "\n"
        private const val MAX_CHARACTER_LINE = 32

        private const val ALIGN_CENTER = 1
        private const val ALIGN_LEFT = 2
        private const val ALIGN_RIGHT = 3
    }

    private val mContext = context

    fun getTestText(): List<String> {
        return listOf(
            getString(
                ALIGN_CENTER,
                mContext.getString(R.string.text_printer_working)
            ) + getEmptyLines(5)
        )
    }

    private fun getEmptyLines(count: Int): String {
        var line = ""
        for (i in 1..count) {
            line += EMPTY_LINE
        }
        return line
    }

    private fun getString(align: Int, string: String): String {
        return when (align) {
            ALIGN_CENTER -> StringUtils.center(string, MAX_CHARACTER_LINE)
            ALIGN_RIGHT -> StringUtils.right(string, MAX_CHARACTER_LINE)
            else -> StringUtils.left(string, MAX_CHARACTER_LINE)
        }
    }
}