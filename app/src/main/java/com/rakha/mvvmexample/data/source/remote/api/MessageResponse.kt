package com.rakha.mvvmexample.data.source.remote.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 *   Created By rakha
 *   2020-01-23
 */
class MessageResponse {
    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("body")
    @Expose
    var body: String? = null
}
