package com.iotric.doctorplus.model

import com.google.gson.annotations.SerializedName

data class PuppyResponse(

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("version")
	val version: Double? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem>? = null
)

data class ResultsItem(

	@field:SerializedName("thumbnail")
	val thumbnail: String? = null,

	@field:SerializedName("ingredients")
	val ingredients: String? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)
