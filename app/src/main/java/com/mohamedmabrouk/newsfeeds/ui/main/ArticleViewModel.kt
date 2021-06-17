package com.mohamedmabrouk.newsfeeds.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mohamedmabrouk.newsfeeds.data.ArticlesClient
import com.mohamedmabrouk.newsfeeds.data.ArticlesInterface
import com.mohamedmabrouk.newsfeeds.model.ArticleModel
import com.mohamedmabrouk.newsfeeds.model.GetArticlesResponse
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException


class ArticleViewModel : ViewModel() {
    val TAG = "ArticleViewModel"

    var articlesMutableLiveData: MutableLiveData<List<ArticleModel>> =
        MutableLiveData<List<ArticleModel>>()
    var errorMsgLiveData: MutableLiveData<String> = MutableLiveData<String>()

    fun getArticles(apiKey: String) {
        // associated press
        val observable1 = ArticlesClient.build().create(ArticlesInterface::class.java)
            .getArticles("associated-press", apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        // the next web
        val observable2 = ArticlesClient.build().create(ArticlesInterface::class.java)
            .getArticles("the-next-web", apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


        val observer = object : Observer<GetArticlesResponse> {

            override fun onNext(articlesResponse: GetArticlesResponse) {
                Log.d(TAG, "onNext: $articlesResponse")
                articlesMutableLiveData.value = articlesResponse.articleModels
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete: ")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError: ${e.message}")
                if (e is HttpException) {
                    Log.i(TAG, "Status code: " + e.code())
                    Log.i(TAG, "Response: " + e.response()?.errorBody()?.string())

                    var errorMsg = ""
                    when (e.code()) {
                        400 -> errorMsg = "Bad Request"
                        401 -> errorMsg = "Unauthorized"
                        429 -> errorMsg = "Too Many Requests"
                        500 -> errorMsg = "Server Error"
                    }
                    errorMsgLiveData.value = "Error: $errorMsg"

                } else {
                    errorMsgLiveData.value = "Error: ${e.message}"
                }
            }

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "onSubscribe: ")
            }

        }

        Observable.zip(
            observable1.subscribeOn(Schedulers.io()),
            observable2.subscribeOn(Schedulers.io()),
            BiFunction { firstResponse: GetArticlesResponse,
                         secondResponse: GetArticlesResponse ->
                combineResult(firstResponse, secondResponse)
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun combineResult(
        result1: GetArticlesResponse,
        result2: GetArticlesResponse
    ): GetArticlesResponse {
        result1.articleModels!!.addAll(result2.articleModels as MutableList<ArticleModel>)
        return result1
    }
}