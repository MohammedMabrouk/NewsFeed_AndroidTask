package com.mohamedmabrouk.newsfeeds.ui.main

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohamedmabrouk.newsfeeds.R
import com.mohamedmabrouk.newsfeeds.model.ArticleModel
import com.mohamedmabrouk.newsfeeds.ui.articleDetails.ArticleDetailsActivity
import kotlinx.android.synthetic.main.fragment_articles_list.view.*

class ArticlesListFragment : Fragment(), ArticlesAdapter.RecyclerViewClickListener {

    private lateinit var rootView: View
    private lateinit var articlesViewModel: ArticleViewModel

    private lateinit var apiKey: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_articles_list, container, false)
        articlesViewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)

        // API KEY
        val ai: ApplicationInfo = requireActivity().applicationContext.packageManager
            .getApplicationInfo(
                requireActivity().applicationContext.packageName,
                PackageManager.GET_META_DATA
            )
        apiKey = ai.metaData["apiKey"].toString()


        getArticles(apiKey)

        val adapter = ArticlesAdapter(activity as Context, null, this)
        rootView.articles_rv.layoutManager = LinearLayoutManager(activity as Context)
        rootView.articles_rv.adapter = adapter


        val articlesObserver = Observer<List<ArticleModel>> { articlesList ->
            rootView.loading_pb.visibility = View.INVISIBLE
            adapter.setList(articlesList)
        }
        articlesViewModel.articlesMutableLiveData.observe(viewLifecycleOwner, articlesObserver)

        val errorObserver = Observer<String> { msg ->
            rootView.loading_pb.visibility = View.INVISIBLE
            rootView.retry_btn.visibility = View.VISIBLE
            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
        }
        articlesViewModel.errorMsgLiveData.observe(viewLifecycleOwner, errorObserver)

        rootView.retry_btn.setOnClickListener {
            rootView.retry_btn.visibility = View.INVISIBLE
            getArticles(apiKey)
        }

        return rootView
    }

    private fun getArticles(apiKey: String) {
        articlesViewModel.getArticles(apiKey)
        rootView.loading_pb.visibility = View.VISIBLE
    }

    override fun onClick(articleModel: ArticleModel?) {
        val intent = Intent(activity, ArticleDetailsActivity::class.java)
        intent.putExtra("articleObject", articleModel)
        startActivity(intent)
    }
}