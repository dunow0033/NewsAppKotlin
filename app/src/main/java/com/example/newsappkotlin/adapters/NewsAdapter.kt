package com.example.newsappkotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappkotlin.SelectListener
import com.example.newsappkotlin.data.model.NewsApiResponse
import com.example.newsappkotlin.data.model.NewsHeadlines
import com.example.newsappkotlin.databinding.HeadlineListItemsBinding
import com.squareup.picasso.Picasso


class NewsAdapter(val context: Context) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            HeadlineListItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        holder.binding.textTitle.text = news[position].title
        holder.binding.textSource.text = news[position].source.toString()

//        if (headlines.get(position).getUrlToImage() != null) {
//            Picasso.get().load(headlines.get(position).getUrlToImage()).into(holder.binding.imgHeadline)
//        }
    }

    override fun getItemCount(): Int {
        return news.size
    }

    private val diffCallback = object : DiffUtil.ItemCallback<NewsHeadlines>() {
        override fun areItemsTheSame(oldItem: NewsHeadlines, newItem: NewsHeadlines): Boolean {
            return oldItem.title == newItem.title
        }
        override fun areContentsTheSame(oldItem: NewsHeadlines, newItem: NewsHeadlines): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    var news: List<NewsHeadlines>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    inner class NewsViewHolder(val binding: HeadlineListItemsBinding) : RecyclerView.ViewHolder(binding.root)
//    {
//        fun bind(newsHeadlines: NewsHeadlines) {
//            binding.textTitle.text = newsHeadlines.title.toString()
//            binding.textSource.text = newsHeadlines.source.toString()
//
//            //binding.imgHeadline = Glide.with(requireContext()).load(newsHeadlines.urlToImage)
//        }
//    }
}