package com.example.cryptonews

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptonews.adapters.NewsListAdapter
import com.example.cryptonews.di.DaggerViewModelFactory
import com.example.cryptonews.util.ImageHelper
import com.example.cryptonews.viewmodel.NewsListViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_loading.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory
    lateinit var viewModel: NewsListViewModel
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var adapter: NewsListAdapter
    @Inject
    lateinit var imageHelper: ImageHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsListViewModel::class.java)
        viewModel.getData()


        adapter = NewsListAdapter(imageHelper, this)
        layoutManager = LinearLayoutManager(this)
        newsRecyclerView.adapter = adapter
        newsRecyclerView.layoutManager = layoutManager

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
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        retryBtn.setOnClickListener {
            viewModel.retry()
        }

    }
}
