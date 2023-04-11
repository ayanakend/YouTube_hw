package com.example.youtube.ui.playlist

import androidx.lifecycle.LiveData
import com.example.youtube.App
import com.example.youtube.core.network.result.Resource
import com.example.youtube.core.ui.BaseViewModel
import com.example.youtube.remote.model.Playlist

class PlaylistViewModel : BaseViewModel() {
    fun getPlayLists(): LiveData<Resource<Playlist>> {
        return App().repository.getPlaylist()
    }
}