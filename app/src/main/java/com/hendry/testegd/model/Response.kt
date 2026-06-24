package com.hendry.testegd.model

import com.google.gson.annotations.SerializedName

data class ResponseModel(

	@field:SerializedName("quote")
	val quote: Quote? = null,

	@field:SerializedName("qotd_date")
	val qotdDate: String? = null
)

data class Quote(

	@field:SerializedName("private")
	val jsonMemberPrivate: Boolean? = null,

	@field:SerializedName("favorites_count")
	val favoritesCount: Int? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("dialogue")
	val dialogue: Boolean? = null,

	@field:SerializedName("upvotes_count")
	val upvotesCount: Int? = null,

	@field:SerializedName("author_permalink")
	val authorPermalink: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("body")
	val body: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("tags")
	val tags: List<String?>? = null,

	@field:SerializedName("downvotes_count")
	val downvotesCount: Int? = null
)
