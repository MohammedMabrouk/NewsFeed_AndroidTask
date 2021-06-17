package com.mohamedmabrouk.newsfeeds.data

import com.mohamedmabrouk.newsfeeds.model.GetArticlesResponse
import io.reactivex.Observable

import retrofit2.http.GET
import retrofit2.http.Query

interface ArticlesInterface {
    @GET("articles")
    fun getArticles(
        @Query("source") source: String,
        @Query("apiKey") apiKey: String
    ): Observable<GetArticlesResponse>
}