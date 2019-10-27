package com.example.cryptonews.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptonews.R
import com.example.cryptonews.data.model.NewsItem
import com.example.cryptonews.util.ImageHelper
import kotlinx.android.synthetic.main.news_list_item.view.*

class NewsListAdapter(
    private val helper: ImageHelper,
    private val context: Context
) : RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder>() {

    private var newsItems: MutableList<NewsItem> = ArrayList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.news_list_item, parent, false)
        return NewsListViewHolder(view)
    }


    fun setList(items: List<NewsItem>) {
        this.newsItems.clear()
        this.newsItems.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return newsItems.size
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        val item = newsItems[position]
        holder.bind(item)
    }

    inner class NewsListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: NewsItem) {
            itemView.textTitle.text = item.title
            itemView.textSource.text = item.sourceDomain
            itemView.textCategory.text = item.primaryCategory
            helper.loadImageOfflineFirst(
                item.originalImageUrl,
                itemView.newsImage,
                R.drawable.image_ph
            )
        }
    }


}