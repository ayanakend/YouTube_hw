package com.example.youtube.ui.playlist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.youtube.core.ui.loadImage
import com.example.youtube.databinding.PlaylistItemBinding
import com.example.youtube.remote.model.Item

class PlaylistAdapter(private val onClick: (Item) -> Unit) :
    Adapter<PlaylistAdapter.PlaylistViewHolder>() {
    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Item>) {
        this.list = list as ArrayList<Item>
        notifyDataSetChanged()
    }

    private var list = arrayListOf<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(
            PlaylistItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class PlaylistViewHolder(private val binding: PlaylistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Item) {
            binding.playlistImg.loadImage(item.snippet.thumbnails.default.url)
            binding.playlistTitle.text = item.snippet.title
            binding.playlistDesc.text = "${item.contentDetails.itemCount} video series"
            binding.item.setOnClickListener {
                onClick.invoke(item)
            }
        }
    }
}
