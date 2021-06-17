package com.xnova.digicerto.services.repository.remote

import android.content.Context
import com.xnova.digicerto.services.repository.constants.FTPConstants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPReply
import java.io.File
import java.io.FileOutputStream

class FTPRepository(context: Context, private val host: String, private val port: Int) {
    private val mBasePath: String = context.filesDir.absolutePath + FTPConstants.FOLDER
    private val mFtp = FTPClient()

    init {
        createBasePath()
    }

    fun connect(login: String, password: String): Completable =
        Completable.create { emitter ->
            mFtp.connect(host, port)
            if (FTPReply.isPositiveCompletion(mFtp.replyCode)) {
                if (!mFtp.login(login, password))
                    emitter.onError(Exception())

                configuration()
                emitter.onComplete()
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun changeDirectory(remoteDirectory: String): Completable =
        Completable.create { emitter ->
            if (mFtp.changeWorkingDirectory(remoteDirectory)) {
                emitter.onComplete()
            } else {
                emitter.onError(Exception())
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun createDirectory(remoteDirectory: String): Completable =
        Completable.create { emitter ->
            if (mFtp.makeDirectory(remoteDirectory)) {
                emitter.onComplete()
            } else {
                emitter.onError(Exception())
            }
        }

    fun downloadFile(remoteDirectory: String, fileName: String): Single<String> =
        Single.create<String> { emitter ->
            val filePath = mBasePath + fileName
            val file = FileOutputStream(filePath)

            if (mFtp.retrieveFile(remoteDirectory + fileName, file)) {
                emitter.onSuccess(filePath)
            } else {
                emitter.onError(Exception())
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun uploadFile(remoteDirectory: String): Boolean {
        return try {
            //mFtp.ret
            true
        } catch (e: java.lang.Exception) {
            false
        }
    }

    fun dispose() {
        mFtp.disconnect()
    }

    private fun configuration() {
        mFtp.connectTimeout = FTPConstants.TIMEOUT_CONNECT
        mFtp.defaultTimeout = FTPConstants.TIMEOUT_DEFAULT
        mFtp.type(FTPClient.BINARY_FILE_TYPE);
        mFtp.enterLocalPassiveMode()
    }

    private fun createBasePath() {
        val basePath = File(mBasePath)
        if (!basePath.exists())
            basePath.mkdir()
    }
}