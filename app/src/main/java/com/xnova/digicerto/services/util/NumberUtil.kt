package com.xnova.digicerto.services.util

class NumberUtil private constructor() {
    companion object {
        fun isInt(value: String): Boolean = value.toIntOrNull() != null

        fun isBlankOrInt(value: String): Boolean =
            value.isBlank() || value.toIntOrNull() != null

        fun isBlankOrDouble(value: String): Boolean =
            value.isBlank() || value.toDoubleOrNull() != null
    }
}