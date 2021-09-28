package com.xnova.digicerto.models

import com.xnova.digicerto.services.enums.AlertType

data class Alert(val alertType: AlertType, val titleId: Int, val messageId: Int)