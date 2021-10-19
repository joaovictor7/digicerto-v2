package com.xnova.digicerto.services.listeners

import com.xnova.digicerto.models.Register

interface RegisterListener {

    fun onClick(register: Register)
}