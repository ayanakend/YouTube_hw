package com.example.youtube.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {

    lateinit var binding: VB
    protected abstract val viewModel: VM
    protected abstract fun inflateViewBinding(inflater: LayoutInflater): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateViewBinding(layoutInflater)
        setContentView(binding.root)

        checkInternet()
        initViewModel()
        initView()
        initListener()
        initRV()
    }

    open fun initView() {}
    open fun checkConnection() {}
    open fun initListener() {}
    open fun checkInternet() {}
    open fun initViewModel() {}
    open fun initRV() {}

}