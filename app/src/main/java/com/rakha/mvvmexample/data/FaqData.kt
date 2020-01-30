package com.rakha.mvvmexample.data

import com.google.gson.annotations.SerializedName

data class FaqData(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("details")
	val details: List<DetailsItem?>? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("body")
	val body: String? = null
)