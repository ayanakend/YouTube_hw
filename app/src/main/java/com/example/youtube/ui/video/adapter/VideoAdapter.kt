package com.example.youtube.ui.video.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.youtube.core.ui.loadImage
import com.example.youtube.databinding.VideosItemBinding
import com.example.youtube.remote.model.Item

class VideoAdapter(private val onClick: (item: Item) -> Unit) :
    RecyclerView.Adapter<VideoAdapter.PlaylistItemViewHolder>() {
    private val data = arrayListOf<Item>()

    @SuppressLint("NotifyDataSetChanged")
    fun addData(newData: List<Item>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistItemViewHolder {
        return PlaylistItemViewHolder(
            VideosItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlaylistItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class PlaylistItemViewHolder(private val binding: VideosItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            with(binding) {
                image.loadImage(item.snippet.thumbnails.medium.url)
                titleTv.text = item.snippet.title
                itemView.setOnClickListener { onClick.invoke(item) }
            }
        }
    }
}