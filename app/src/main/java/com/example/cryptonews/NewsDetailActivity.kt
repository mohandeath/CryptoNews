package com.example.cryptonews

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cryptonews.di.DaggerViewModelFactory
import com.example.cryptonews.util.ImageHelper
import com.example.cryptonews.viewmodel.NewsDetailViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_news_detail.*
import javax.inject.Inject


class NewsDetailActivity : DaggerAppCompatActivity() {


    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory
    lateinit var viewModel: NewsDetailViewModel
    @Inject
    lateinit var imageHelper: ImageHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        //removing toolbar programatically for this specific one
        supportActionBar?.hide()
        backBtn.setOnClickListener { super.onBackPressed() }
        val newsId = intent.getStringExtra(NEWS_ID)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(NewsDetailViewModel::class.java)
        viewModel.newsId = newsId

        viewModel.newsItemData.observe(this, Observer {
            it.originalImageUrl?.let { url ->
                imageHelper.loadImageOfflineFirst(url, imageCover)
            }

            textDescription.text = it.description
            textTitle.text = it.title
            textSourceUrl.text = it.sourceDomain
            btnReadMore.setOnClickListener { view ->
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
                startActivity(browserIntent)
            }
        })


    }
}