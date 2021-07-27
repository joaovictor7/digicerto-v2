package com.xnova.digicerto.services.repositories.remote

import android.content.Context
import com.xnova.digicerto.services.constants.FTPConstants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPReply
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class FTPRepository(context: Context, private val host: String, private val port: Int) {
    private val mBasePath: String = context.filesDir.absolutePath + FTPConstants.FOLDER
    private val mFtp = FTPClient()
    private val mDateFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("GMT-00:00")
    }

    init {
        createBasePath()
    }

    fun connect(login: String, password: String): Observable<Boolean> {
        return Observable.create { emitter ->
            mFtp.connect(host, port)
            if (FTPReply.isPositiveCompletion(mFtp.replyCode)) {
                if (!mFtp.login(login, password))
                    emitter.onError(Exception())

                configuration()
                emitter.onNext(true)
                emitter.onComplete()
            }
        }

    }

    fun fileModificationDate(fileDirectory: String): Observable<Calendar> {
        return Observable.create { emitter ->
            val date = mFtp.getModificationTime(fileDirectory)
            val calendar = Calendar.getInstance()
            calendar.time = mDateFormat.parse(date)!!
            emitter.onNext(calendar)
            emitter.onComplete()
        }
    }

    fun changeDirectory(remoteDirectory: String): Completable {
        return Completable.create { emitter ->
            if (mFtp.changeWorkingDirectory(remoteDirectory)) {
                emitter.onComplete()
            } else {
                emitter.onError(Exception())
            }
        }
    }


    fun createDirectory(remoteDirectory: String): Completable {
        return Completable.create { emitter ->
            if (mFtp.makeDirectory(remoteDirectory)) {
                emitter.onComplete()
            } else {
                emitter.onError(Exception())
            }
        }
    }

    fun downloadFile(remoteDirectory: String, fileName: String): Observable<String> {
        return Observable.create { emitter ->
            val filePath = mBasePath + fileName
            val file = FileOutputStream(filePath)

            if (mFtp.retrieveFile(remoteDirectory + fileName, file)) {
                emitter.onNext(filePath)
            } else {
                emitter.onError(Exception())
            }

            emitter.onComplete()
        }
    }

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
        mFtp.type(FTPClient.BINARY_FILE_TYPE)
        mFtp.enterLocalPassiveMode()
    }

    private fun createBasePath() {
        val basePath = File(mBasePath)
        if (!basePath.exists())
            basePath.mkdir()
    }
}