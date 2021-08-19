package com.xnova.digicerto.services.repositories.local

import android.content.Context
import com.xnova.digicerto.models.entities.Producer
import com.xnova.digicerto.services.data.DatabaseService

class ProducerRepository(context: Context) {

    private val mProducerDao = DatabaseService.getDatabase(context).producerDao()

    fun addOrUpdate(producer: Producer) {
        val p = get(producer.code)
        if (p == null)
            mProducerDao.add(producer) else mProducerDao.update(producer)
    }

    fun get(id: Int): Producer? {
        return mProducerDao.get(id)
    }

    fun inactiveAll() {
        mProducerDao.inactiveAll()
    }
}