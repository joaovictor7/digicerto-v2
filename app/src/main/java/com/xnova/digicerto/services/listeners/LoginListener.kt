package com.xnova.digicerto.services.listeners

interface LoginListener {
    fun authenticate(authenticated: Boolean)
}