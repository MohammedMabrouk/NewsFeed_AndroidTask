package com.mohamedmabrouk.newsfeeds.ui.articleDetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.mohamedmabrouk.newsfeeds.R
import com.mohamedmabrouk.newsfeeds.model.ArticleModel
import com.mohamedmabrouk.newsfeeds.utils.DateFormat
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_article_details.*

class ArticleDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)

        // Setting the Toolbar
        val mToolBar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolBar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)


        val articleModel: ArticleModel? = intent.getParcelableExtra("articleObject")
        if(articleModel != null){
            article_title_textView.text = articleModel.title
            article_author_textView.text = resources.getString(R.string.author_str, articleModel.author)
            article_description_textView.text = articleModel.description
            article_publishDate_textView.text = DateFormat.changeDateFormat(
                articleModel.publishedAt,
                DateFormat.ARTICLE_DATE_FORMAT,
                DateFormat.LAYOUT_DATE_FORMAT
            )

            Picasso.get()
                .load(articleModel.urlToImage)
                .placeholder(R.drawable.placeholder)
                .into(article_thumbnail_imageView)

            open_website_btn.setOnClickListener{
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(articleModel.url))
                startActivity(browserIntent)
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}