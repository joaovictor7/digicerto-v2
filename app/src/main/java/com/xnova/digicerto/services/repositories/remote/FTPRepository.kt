package com.xnova.digicerto.services.repositories.remote

import android.content.Context
import com.xnova.digicerto.R
import com.xnova.digicerto.services.constants.FTPConstants
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPReply
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class FTPRepository(context: Context, host: String, port: Int) {

    private val mContext = context
    private val mHost = host
    private val mPort = port
    private val mBasePath: String = context.filesDir.absolutePath + FTPConstants.FOLDER
    private val mFtp = FTPClient().apply {
        connectTimeout = FTPConstants.TIMEOUT_CONNECT
        defaultTimeout = FTPConstants.TIMEOUT_DEFAULT
    }
    private val mDateFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("GMT-00:00")
    }

    init {
        createBasePath()
    }

    fun connect(login: String, password: String): Observable<Boolean> {
        return Observable.create { emitter ->
            try {
                mFtp.connect(mHost, mPort)
                if (FTPReply.isPositiveCompletion(mFtp.replyCode)) {
                    mFtp.type(FTPClient.BINARY_FILE_TYPE)
                    mFtp.enterLocalPassiveMode()
                    if (mFtp.login(login, password)) {
                        emitter.onNext(true)
                    } else {
                        val msg = mContext.getString(R.string.msg_autentication_ftp_failed)
                        emitter.onError(Exception(msg))
                    }
                }
            } catch (e: Exception) {
                val msg = mContext.getString(R.string.msg_connect_ftp_failed)
                emitter.onError(Exception(msg))
            } finally {
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

    private fun createBasePath() {
        val basePath = File(mBasePath)
        if (!basePath.exists())
            basePath.mkdir()
    }
}