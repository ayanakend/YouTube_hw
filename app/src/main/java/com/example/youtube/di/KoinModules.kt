package com.example.youtube.di

import com.example.youtube.core.network.networkModule
import com.example.youtube.remote.remoteDataSource
import org.koin.core.module.Module

val koinModules = listOf<Module>(
    repoModules,
    viewModules,
    remoteDataSource,
    networkModule
)