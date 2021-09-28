package com.xnova.digicerto.services.repositories.local.entities

import android.content.Context
import com.xnova.digicerto.models.entities.Credential
import com.xnova.digicerto.services.data.DatabaseService

class CredentialRepository(context: Context) {

    private val mCredentialDao = DatabaseService.getDatabase(context).credentialDao()

    fun addOrUpdate(credential: Credential) {
        val c = get()
        if (c == null)
            mCredentialDao.add(credential) else mCredentialDao.update(credential)
    }

    fun get(): Credential? {
        return mCredentialDao.get()
    }
}