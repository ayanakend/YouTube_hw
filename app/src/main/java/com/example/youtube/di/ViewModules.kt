package com.example.youtube.di

import com.example.youtube.ui.player.PlayerViewModel
import com.example.youtube.ui.playlist.PlaylistViewModel
import com.example.youtube.ui.video.VideoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModules = module {
    viewModel{PlaylistViewModel(get())}
    viewModel{ VideoViewModel(get()) }
    viewModel{PlayerViewModel(get())}
}