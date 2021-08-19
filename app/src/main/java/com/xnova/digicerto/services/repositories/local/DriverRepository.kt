package com.xnova.digicerto.services.repositories.local

import android.content.Context
import com.xnova.digicerto.models.entities.Driver
import com.xnova.digicerto.services.data.DatabaseService

class DriverRepository(context: Context) {
    
    private val mDriverDao = DatabaseService.getDatabase(context).driverDao()

    fun addOrUpdate(driver: Driver) {
        val o = get(driver.code)
        if (o == null)
            mDriverDao.add(driver) else mDriverDao.update(driver)
    }

    fun get(id: Int): Driver? {
        return mDriverDao.get(id)
    }

    fun inactiveAll() {
        mDriverDao.inactiveAll()
    }
}