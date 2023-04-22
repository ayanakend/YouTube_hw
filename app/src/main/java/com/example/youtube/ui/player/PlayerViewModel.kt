package com.example.youtube.ui.player

import androidx.lifecycle.LiveData
import com.example.youtube.core.network.result.Resource
import com.example.youtube.core.ui.BaseViewModel
import com.example.youtube.remote.model.Video
import com.example.youtube.repository.Repository

class PlayerViewModel (private val repository: Repository): BaseViewModel() {
    fun getVideo(id:String): LiveData<Resource<Video>> {
        return repository.getVideo(id)
    }
}