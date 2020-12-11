package com.rakha.mvvmexample.data

import com.google.gson.annotations.SerializedName

data class FaqData(

	@field:SerializedName("image")
	var image: String? = null,

	@field:SerializedName("details")
	var details: List<DetailsItem?>? = null,

	@field:SerializedName("title")
	var title: String? = null,

	@field:SerializedName("body")
	var body: String? = null
)