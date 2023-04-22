package com.example.youtube.ui.video

import androidx.lifecycle.LiveData
import com.example.youtube.core.network.result.Resource
import com.example.youtube.core.ui.BaseViewModel
import com.example.youtube.remote.model.PlaylistItem
import com.example.youtube.repository.Repository

class VideoViewModel(private val repository: Repository) : BaseViewModel() {

    fun getVideos(playlistId: String, itemCount: Int): LiveData<Resource<PlaylistItem>> {
        return repository.getVideos(playlistId, itemCount)
    }
}