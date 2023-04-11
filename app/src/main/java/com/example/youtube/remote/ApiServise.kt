package com.example.youtube.remote

import android.provider.MediaStore
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("playlists")
    fun getPlayLists(
        @Query("key") key:String,
        @Query("part") part:String,
        @Query("channelId") channelId: String,
    ) : Call<MediaStore.Audio.Playlists>
}