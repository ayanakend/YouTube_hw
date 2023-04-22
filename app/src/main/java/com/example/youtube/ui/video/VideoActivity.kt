package com.example.youtube.ui.video

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.youtube.core.network.isOnline.ConnectionLiveData
import com.example.youtube.core.network.result.Status
import com.example.youtube.core.ui.BaseActivity
import com.example.youtube.databinding.ActivityVideoBinding
import com.example.youtube.remote.model.Item
import com.example.youtube.remote.model.PlaylistInfo
import com.example.youtube.ui.player.PlayerActivity
import com.example.youtube.ui.playlist.PlaylistViewModel
import com.example.youtube.ui.video.adapter.VideoAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoActivity : BaseActivity<ActivityVideoBinding, VideoViewModel>() {
    private val adapter by lazy { VideoAdapter(this::onCLick) }
    private val playlistInfo by lazy { intent.getSerializableExtra("VIDEOS_KEY") as PlaylistInfo }
    private var playlistItemData = listOf<Item>()
    override val viewModel: VideoViewModel by viewModel()

    override fun checkConnection() {
        super.checkConnection()
        ConnectionLiveData(application).observe(this) { isConnected ->
            if (isConnected) {
                binding.noInternet.visibility = View.GONE
                binding.content.visibility = View.VISIBLE
            } else {
                binding.noInternet.visibility = View.VISIBLE
                binding.content.visibility = View.GONE
            }
        }
    }

    override fun initView() {
        super.initView()
        binding.videosRv.adapter = adapter
        binding.titleTv.text = playlistInfo.title
        binding.descTv.text = playlistInfo.desc
        binding.tvCounterVideo.text = "${playlistInfo.itemCount} video series "
    }

    override fun initViewModel() {
        super.initViewModel()
        getVideos()
    }

    private fun getVideos() {
        viewModel.getVideos(playlistInfo.id, playlistInfo.itemCount).observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    playlistItemData = it.data!!.items
                    adapter.addData(playlistItemData)
                }
                Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> Log.e("aga", "LOADING: ")
            }
        }
    }

    override fun inflateViewBinding(): ActivityVideoBinding {
        return ActivityVideoBinding.inflate(layoutInflater)
    }

    override fun initListener() {
        super.initListener()
        binding.backTv.setOnClickListener { finish() }
        binding.backImg.setOnClickListener { finish() }
    }

    private fun onCLick(item:Item) {
        val intent = Intent(this, PlayerActivity::class.java)
        intent.putExtra("id", item.contentDetails.videoId)
        intent.putExtra("title", item.snippet.title)
        intent.putExtra("desc", item.snippet.description)
        startActivity(intent)
    }
}