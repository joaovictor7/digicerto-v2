package com.xnova.digicerto.services.listeners

import com.xnova.digicerto.models.MenuSettings

interface MenuSettingsListener {

    fun onClick(menu: MenuSettings)
}