package com.rakha.mvvmexample.data.source.remote.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 *   Created By rakha
 *   2020-01-23
 */
class BasicResponse<T> {
    @SerializedName("rescode")
    @Expose
    var rescode: String? = null

    @SerializedName("message")
    @Expose
    var message: MessageResponse? = null

    @SerializedName("data")
    @Expose
    var data: T? = null
}
