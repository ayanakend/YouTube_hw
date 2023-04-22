package com.example.youtube.ui.player

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.youtube.core.network.isOnline.ConnectionLiveData
import com.example.youtube.core.ui.BaseActivity
import com.example.youtube.databinding.ActivityPlayerBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.util.Util
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerActivity  : BaseActivity<ActivityPlayerBinding, PlayerViewModel>() {
    companion object{
        val VIDEO_KEY = "VIDEO_KEY"
    }
    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L
    override val viewModel:  PlayerViewModel by viewModel()

    private fun initializePlayer() {
        val  youtubeLink = "http://youtube.com/watch?v=${intent.getStringExtra("id")}"

        player = ExoPlayer.Builder(this)
            .build()
            .also { exoPlayer ->
                binding.video.player = exoPlayer
                val mediaItem = MediaItem.fromUri("https://www.youtube.com/watch?v=sr8TUj3wH8E&t=404s")
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.seekTo(currentItem, playbackPosition)
                exoPlayer.prepare()
            }
    }
    override fun inflateViewBinding(): ActivityPlayerBinding {
        return ActivityPlayerBinding.inflate(layoutInflater)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun checkConnection() {
        super.checkConnection()
        val connection = ConnectionLiveData(application)
        connection.observe(this) { isConnected ->
            if (isConnected) {
                binding.content.visibility = View.VISIBLE
                binding.noInternet.visibility = View.GONE
            } else {
                binding.content.visibility = View.GONE
                binding.noInternet.visibility = View.VISIBLE
            }
        }
    }



    override fun initViewModel() {
        super.initViewModel()
        val getId = intent.getStringExtra("id")
        val getTitle = intent.getStringExtra("title")
        val getDesc = intent.getStringExtra("desc")
        viewModel.getVideo(getId!!).observe(this) {
            binding.titleTv.text = getTitle
            binding.descTv.text = getDesc
        }
    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUi()
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer()
        }
    }
    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.video).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    override fun initListener() {
        super.initListener()
        binding.backImg.setOnClickListener { finish() }
        binding.backTv.setOnClickListener { finish() }
    }

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentItem = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
        player = null
    }
}