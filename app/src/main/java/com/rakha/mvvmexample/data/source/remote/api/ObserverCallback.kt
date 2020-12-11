package com.rakha.mvvmexample.data.source.remote.api

import org.json.JSONObject
import okhttp3.ResponseBody
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException
import java.io.IOException


/**
 *   Created By rakha
 *   2020-01-29
 */
abstract class ObserverCallback<T> : DisposableObserver<T>() {

    override fun onNext(t: T) {
        val response = t as Response<*>
        if (response.isSuccessful()) {
            val baseResponse = response.body() as BasicResponse<T>
            if (baseResponse.rescode.equals("00")) {
                onSuccess(baseResponse.data!!)
            } else {
                val body = baseResponse.message?.body
                onFailed(getErrMessage(body!!))
            }
        } else {
            val code = response.code()

            if (code == 555) {
                onMaintenance()
            } else if (code == 500) {
                onFailed("Server Timeout")
            } else {
                try {
                    val baseResponse = parseError(response)

                    val body = baseResponse.message?.body
                    onFailed(getErrMessage(body!!))
                } catch (e: Exception) {
                    e.printStackTrace()
                    onFailed(e.message)
                }

            }
        }
    }

    override fun onError(e: Throwable) {
        if (e is HttpException) {
            val responseBody = (e as HttpException).response().errorBody()
            onFailed(getErrorMessage(responseBody!!))
        } else if (e is UnknownHostException) {
            onFailed("Ups! Saat ini Anda tidak terhubung dengan internet. Mohon cek koneksi Anda")
        } else if (e is SSLHandshakeException || e is ConnectException) {
            onFailed("Ups! Koneksi internet Anda terputus. Mohon cek koneksi Anda")
        } else {
            onFailed(e.message)
        }
    }

    override fun onComplete() {

    }

    private fun getErrorMessage(responseBody: ResponseBody): String? {
        try {
            val jsonObject = JSONObject(responseBody.string())
            return jsonObject.getString("message")
        } catch (e: Exception) {
            return e.message
        }

    }

    private fun getErrMessage(body: Any): String {
        var errMessage = ""
        if (body is String) {
            errMessage = body.toString()
        } else if (body is ArrayList<*>) {
            val messages = body as ArrayList<String>

            val idx = 0
            for (message in messages) {
                val messageSplit =
                    message.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]

                if (idx == 0) {
                    errMessage += messageSplit
                } else {

                }
            }
        }

        return errMessage
    }

    fun parseError(response: Response<*>): BasicResponse<Any> {
        val converter = ApiService.retrofit.responseBodyConverter<BasicResponse<Any>>(BasicResponse::class.java, arrayOfNulls<Annotation>(0))
        val error: BasicResponse<Any>

        try {
            error = converter.convert(response.errorBody())
        } catch (e: IOException) {
            return BasicResponse()
        }

        return error
    }

    protected abstract fun onSuccess(obj: Any?)

    protected abstract fun onFailed(message: String?)

    protected abstract fun onMaintenance()
}