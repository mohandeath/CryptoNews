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

        setupViewModel()

        supportActionBar?.hide()
        backBtn.setOnClickListener { super.onBackPressed() }

        observeViewModelEvents()


    }

    private fun observeViewModelEvents() {
        viewModel.newsItemData.observe(this, Observer {
            it.originalImageUrl?.let { url ->
                imageHelper.loadImageOfflineFirst(url, imageCover)
            }

            textDescription.text = it.description
            textTitle.text = it.title
            textSourceUrl.text = it.sourceDomain
            btnReadMore.setOnClickListener { _ ->
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
                startActivity(browserIntent)
            }
        })
    }

    private fun setupViewModel() {
        val newsId = intent.getStringExtra(NEWS_ID) ?: "-1"

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(NewsDetailViewModel::class.java)
        viewModel.newsId = newsId

    }
}