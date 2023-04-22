package com.example.youtube.remote

import com.example.youtube.remote.model.Playlist
import com.example.youtube.remote.model.PlaylistItem
import com.example.youtube.remote.model.Video
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("playlists")
    suspend fun getPlayLists(
        @Query("key") key:String,
        @Query("part") part:String,
        @Query("channelId") channelId: String,
    ) : Response<Playlist>
    @GET("playlistItems")

    suspend fun getPlaylistItem(
        @Query("key") key : String,
        @Query("part") part : String,
        @Query("playlistId") channelId : String,
        @Query("maxResults") maxResults : Int
    ) : Response<PlaylistItem>

    @GET("videos")
    suspend fun getVideo(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("id") id: String
    ): Response<Video>
}