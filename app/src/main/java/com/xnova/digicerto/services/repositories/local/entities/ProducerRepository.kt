package com.xnova.digicerto.services.repositories.local.entities

import android.content.Context
import com.xnova.digicerto.models.entities.Producer
import com.xnova.digicerto.services.data.DatabaseService

class ProducerRepository(context: Context) {

    private val mProducerDao = DatabaseService.getDatabase(context).producerDao()

    fun addOrUpdate(producer: Producer) {
        val p = get(producer.code, producer.farmCode)
        if (p == null) {
            mProducerDao.add(producer)
        } else {
            mProducerDao.update(producer)
        }
    }

    fun get(code: Int, farmCode: Int): Producer? {
        return mProducerDao.get(code, farmCode)
    }

    fun inactiveAll() {
        mProducerDao.inactiveAll()
    }

    fun getTotalActive(): Int {
        return mProducerDao.getTotalActive()
    }

    fun getAllActive(): List<Producer> {
        return mProducerDao.getAllActive()
    }
}