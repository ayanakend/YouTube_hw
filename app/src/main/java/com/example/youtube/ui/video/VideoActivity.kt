package com.example.youtube.ui.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.youtube.R
import com.example.youtube.core.network.isOnline.ConnectionLiveData
import com.example.youtube.core.ui.BaseActivity
import com.example.youtube.databinding.ActivityVideoBinding
import com.example.youtube.ui.playlist.PlaylistViewModel

class VideoActivity : BaseActivity<ActivityVideoBinding, PlaylistViewModel>(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
    }

    override val viewModel: PlaylistViewModel by lazy { ViewModelProvider(this)[PlaylistViewModel::class.java] }

    override fun checkConnection() {
        super.checkInternet()
        ConnectionLiveData(application).observe(this) { isConnected ->
            if (isConnected) {
                binding.content.visibility = View.VISIBLE
                binding.noInet.visibility = View.GONE
            } else {
                binding.content.visibility = View.GONE
                binding.noInet.visibility = View.VISIBLE
            }
        }
    }
    override fun initListener() {
        super.initListener()
        val result = intent.getStringExtra("id")
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityVideoBinding {
        return ActivityVideoBinding.inflate(layoutInflater)
    }
}