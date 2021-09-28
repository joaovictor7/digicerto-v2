package com.xnova.digicerto.models.entities.settings

import androidx.room.ColumnInfo

data class FileSettings(
    @ColumnInfo(name = "CollectCancelled") val collectCancelled: Boolean
)