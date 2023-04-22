package com.example.youtube.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {

    protected lateinit var binding: VB
    protected abstract val viewModel: VM
    protected abstract fun inflateViewBinding(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateViewBinding()
        setContentView(binding.root)

        checkConnection()
        initViewModel()
        initView()
        initRV()
        initListener()
    }

    open fun initRV() {}
    open fun initViewModel() {}
    open fun checkConnection() {}
    open fun initView() {}
    open fun initListener() {}
}