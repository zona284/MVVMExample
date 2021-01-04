package com.rakha.mvvmexample.data.source.remote.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 *   Created By rakha
 *   17/12/20
 */
class PagingResponse<T> {

    @SerializedName("data")
    @Expose
    var data: MutableList<T>? = null
}