package com.xnova.digicerto.services.repositories.local

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {

    private val mFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss", Locale.getDefault())

    @TypeConverter
    fun calendarToString(calendar: Calendar?): String? {
        return if (calendar != null) {
            mFormat.format(calendar.time)
        } else {
            null
        }
    }

    @TypeConverter
    fun stringToCalendar(string: String?): Calendar? {
        val calendar = Calendar.getInstance()

        return if (string != null) {
            calendar.time = mFormat.parse(string)!!
            calendar
        } else {
            null
        }
    }
}