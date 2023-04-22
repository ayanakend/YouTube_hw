package com.example.youtube.ui.playlist

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtube.core.network.isOnline.ConnectionLiveData
import com.example.youtube.core.network.result.Status
import com.example.youtube.core.ui.BaseActivity
import com.example.youtube.databinding.PlaylistActivityBinding
import com.example.youtube.remote.model.Item
import com.example.youtube.remote.model.PlaylistInfo
import com.example.youtube.ui.playlist.adapter.PlaylistAdapter
import com.example.youtube.ui.video.VideoActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayListActivity : BaseActivity<PlaylistActivityBinding, PlaylistViewModel>(){
    private val adapter: PlaylistAdapter by lazy { PlaylistAdapter(this::onClick) }

    override val viewModel: PlaylistViewModel by viewModel()

    override fun checkConnection() {
        super.checkConnection()
        val connection = ConnectionLiveData(application)
        connection.observe(this) { isConnected ->
            if (isConnected) {
                binding.rvPlaylist.visibility = View.VISIBLE
                binding.noInet.visibility = View.GONE
            } else {
                binding.rvPlaylist.visibility = View.GONE
                binding.noInet.visibility = View.VISIBLE
            }
        }
    }

    override fun initRV() {
        super.initRV()
        binding.rvPlaylist.layoutManager = LinearLayoutManager(this)
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.loading.observe(this) {
            binding.progressBar.isVisible = it
        }
        viewModel.getPlayLists().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.rvPlaylist.adapter = adapter
                    it.data?.let { it1 -> adapter.setList(it1.items) }
                    viewModel.loading.postValue(false)
                }
                Status.LOADING -> {
                    viewModel.loading.postValue(true)
                }
                Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    viewModel.loading.postValue(false)
                }
            }
        }
    }

    override fun inflateViewBinding(): PlaylistActivityBinding {
        return PlaylistActivityBinding.inflate(layoutInflater)
    }

    private fun onClick(item: Item) {
        val intent = Intent(this, VideoActivity::class.java)
        intent.putExtra(
            "VIDEOS_KEY",
            PlaylistInfo(
                item.id,
                item.snippet.title,
                item.snippet.description,
                item.contentDetails.itemCount
            )
        )
        startActivity(intent)
    }
}