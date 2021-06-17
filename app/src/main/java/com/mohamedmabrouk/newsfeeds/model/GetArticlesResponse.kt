package com.mohamedmabrouk.newsfeeds.model
import com.google.gson.annotations.SerializedName


data class GetArticlesResponse(
    @SerializedName("articles")
    val articleModels: MutableList<ArticleModel>?,
    @SerializedName("sortBy")
    val sortBy: String?,
    @SerializedName("source")
    val source: String?,
    @SerializedName("status")
    val status: String?
)

