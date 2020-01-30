package com.rakha.mvvmexample.data

import com.google.gson.annotations.SerializedName

data class DetailsItem(

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("body")
	val body: String? = null
)