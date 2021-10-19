package com.xnova.digicerto.services.repositories.local.entities

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

    fun get(code: Int): Driver? {
        return mDriverDao.get(code)
    }

    fun getAllActive(): List<Driver> {
        return mDriverDao.getAllActive()
    }

    fun inactiveAll() {
        mDriverDao.inactiveAll()
    }

    fun getTotalActives(): Int {
        return mDriverDao.getTotalActives()
    }
}