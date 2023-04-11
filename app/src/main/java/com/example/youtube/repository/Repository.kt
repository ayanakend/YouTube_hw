package com.example.youtube.repository

import androidx.lifecycle.MutableLiveData
import com.example.youtube.BuildConfig
import com.example.youtube.core.network.RetroFitClient
import com.example.youtube.core.network.result.Resource
import com.example.youtube.remote.ApiService
import com.example.youtube.remote.model.Playlist
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {
    private val apiService: ApiService = RetroFitClient.create()

    fun getPlaylist(): MutableLiveData<Resource<Playlist>> {
        val data = MutableLiveData<Resource<Playlist>>()
        data.value = Resource.loading()
        apiService.getPlayLists(
            BuildConfig.API_KEY,
            "contentDetails,snippet",
            "UCQUCjrISpZyBy16Zyl2Uqvg"
        ).enqueue(object : Callback<Playlist> {
            override fun onResponse(call: Call<Playlist>, response: Response<Playlist>) {
                if (response.isSuccessful) {
                    data.value = Resource.success(response.body())
                }
            }
            override fun onFailure(call: Call<Playlist>, t: Throwable) {
                data.value = Resource.error(t.message, null, t.hashCode())
            }
        })
        return data
    }
}
