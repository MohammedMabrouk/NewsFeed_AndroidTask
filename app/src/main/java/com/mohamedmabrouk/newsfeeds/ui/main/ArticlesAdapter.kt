package com.mohamedmabrouk.newsfeeds.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mohamedmabrouk.newsfeeds.R
import com.mohamedmabrouk.newsfeeds.model.ArticleModel
import com.mohamedmabrouk.newsfeeds.utils.DateFormat
import com.squareup.picasso.Picasso

class ArticlesAdapter(
    private val mContext: Context,
    articleList: List<ArticleModel>?,
    mListener: RecyclerViewClickListener?
) :
    RecyclerView.Adapter<ArticlesAdapter.MyViewHolder?>() {

    private var articleList: List<ArticleModel>? = articleList
    private var mListener: RecyclerViewClickListener? = null

    init {
        this.mListener = mListener
    }

    fun setList(articleList: List<ArticleModel>) {
        this.articleList = articleList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_article, viewGroup, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val article: ArticleModel = articleList!![position]
        if (!article.title.isNullOrEmpty())
            holder.title.text = article.title
        if (!article.author.isNullOrEmpty())
            holder.author.text = mContext.resources.getString(R.string.author_str, article.author)
        if (!article.publishedAt.isNullOrEmpty())
            holder.publishDate.text = DateFormat.changeDateFormat(
                article.publishedAt,
                DateFormat.ARTICLE_DATE_FORMAT,
                DateFormat.LAYOUT_DATE_FORMAT
            )

        Picasso.get()
            .load(article.urlToImage)
            .placeholder(R.drawable.placeholder)
            .into(holder.thumbnail)
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        var title: TextView = view.findViewById<View>(R.id.article_title_textView) as TextView
        var author: TextView = view.findViewById<View>(R.id.article_author_textView) as TextView
        var publishDate: TextView =
            view.findViewById<View>(R.id.article_publishDate_textView) as TextView
        var thumbnail: ImageView =
            view.findViewById<View>(R.id.article_thumbnail_imageView) as ImageView

        override fun onClick(view: View) {
            mListener!!.onClick(articleList!![adapterPosition])
        }

        init {
            view.setOnClickListener(this)
            thumbnail.setOnClickListener(this)
        }
    }

    override fun getItemCount(): Int {
        return if (articleList != null) articleList!!.size else 0
    }

    interface RecyclerViewClickListener {
        fun onClick(articleModel: ArticleModel?)
    }
}