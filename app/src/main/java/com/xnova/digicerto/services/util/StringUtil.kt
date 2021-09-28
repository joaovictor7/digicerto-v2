package com.xnova.digicerto.services.util

class StringUtil private constructor() {

    companion object {
        private const val EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        fun emailValid(email: String): Boolean {
            return email.matches(EMAIL_PATTERN.toRegex())
        }
    }
}