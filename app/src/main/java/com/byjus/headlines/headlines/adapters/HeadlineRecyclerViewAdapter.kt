package com.byjus.headlines.headlines.adapters

import android.os.Build
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.byjus.headlines.R
import com.byjus.headlines.headlines.activity.Headlines
import com.byjus.headlines.headlines.models.response.ArticlesItem
import kotlinx.android.synthetic.main.item_view_headlines.view.*

class HeadlineRecyclerViewAdapter(private val list: List<ArticlesItem>) :
    RecyclerView.Adapter<HeadLineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadLineViewHolder {
        return HeadLineViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_headlines, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HeadLineViewHolder, position: Int) {
        holder.mItemView.tvHeadLineTitle.text = list[position].title
        holder.mItemView.tvSubtitle.text = list[position].author?.trim()
        holder.mItemView.tvDate.text = list[position].publishedAt?.trim()
        Glide.with(holder.mItemView.context)
            .load(list[position].urlToImage)
            .centerCrop()
            .into(holder.mItemView.ivHeadLineImage)
        holder.mItemView.setOnClickListener {
            when (holder.mItemView.context) {
                is Headlines -> {
                    (holder.mItemView.context as Headlines).setDetailFragment(list[position],holder.mItemView)
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size

}

class HeadLineViewHolder(var mItemView: View) : RecyclerView.ViewHolder(mItemView)