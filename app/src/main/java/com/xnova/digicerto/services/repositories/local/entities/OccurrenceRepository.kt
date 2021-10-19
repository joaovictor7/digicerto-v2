package com.xnova.digicerto.services.repositories.local.entities

import android.content.Context
import com.xnova.digicerto.models.entities.Occurrence
import com.xnova.digicerto.services.data.DatabaseService

class OccurrenceRepository(context: Context) {

    private val mOccurrenceDao = DatabaseService.getDatabase(context).occurrenceDao()

    fun addOrUpdate(occurrence: Occurrence) {
        val o = get(occurrence.code)
        if (o == null) {
            mOccurrenceDao.add(occurrence)
        } else {
            mOccurrenceDao.update(occurrence)
        }
    }

    fun get(code: Int): Occurrence? {
        return mOccurrenceDao.get(code)
    }

    fun inactiveAll() {
        mOccurrenceDao.inactiveAll()
    }

    fun getTotalActives(): Int {
        return mOccurrenceDao.getTotalActive()
    }

    fun getAllActive(): List<Occurrence> {
        return mOccurrenceDao.getAllActive()
    }
}