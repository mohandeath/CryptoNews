package com.example.cryptonews

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptonews.adapters.NewsListAdapter
import com.example.cryptonews.di.DaggerViewModelFactory
import com.example.cryptonews.util.ImageHelper
import com.example.cryptonews.viewmodel.NewsListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_loading.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var adapter: NewsListAdapter
    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory
    lateinit var viewModel: NewsListViewModel
    @Inject
    lateinit var imageHelper: ImageHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()

        setupViews()

        observeChanges()

    }

    private fun observeChanges() {
        viewModel.newsList.observe(this, Observer {
            adapter.setList(it)
        })

        viewModel.loadingVisibility.observe(this, Observer {
            loading.visibility = it!!
        })

        viewModel.retryVisibility.observe(this, Observer {
            retryBtn.visibility = it!!
        })

        viewModel.networkError.observe(this, Observer { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {
                Snackbar.make(newsRecyclerView, it, Snackbar.LENGTH_LONG)
                    .setAction("retry") { viewModel.retry() }

                    .show()
            }
        })

    }

    private fun setupViews() {
        adapter = NewsListAdapter(imageHelper, this) {
            val detailIntent = Intent(this, NewsDetailActivity::class.java).apply {
                putExtra(NEWS_ID, it._id)
            }
            startActivity(detailIntent)
        }
        layoutManager = LinearLayoutManager(this)
        newsRecyclerView.adapter = adapter
        newsRecyclerView.layoutManager = layoutManager


        retryBtn.setOnClickListener {
            viewModel.retry()
        }
    }

    fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsListViewModel::class.java)
        viewModel.getData()

    }
}
