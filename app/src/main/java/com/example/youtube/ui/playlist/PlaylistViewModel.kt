package com.example.youtube.ui.playlist

import androidx.lifecycle.LiveData
import com.example.youtube.core.network.result.Resource
import com.example.youtube.core.ui.BaseViewModel
import com.example.youtube.remote.model.Playlist
import com.example.youtube.repository.Repository

class PlaylistViewModel(private val repository: Repository) : BaseViewModel() {
    fun getPlayLists(): LiveData<Resource<Playlist>> {
        return repository.getPlayLists()
    }
}