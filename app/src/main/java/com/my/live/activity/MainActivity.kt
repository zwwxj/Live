package com.my.live.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.my.base.component.activity.BaseDataBindingActivity
import com.my.base.component.activity.TestActivity
import com.my.base.retrofit.RequestStatus
import com.my.live.R
import com.my.live.databinding.ActivityMainBinding
import com.my.live.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseDataBindingActivity<ActivityMainBinding>() {

    private val model: LoginViewModel by viewModels()

    override fun getLayoutId(): Int = R.layout.activity_main

    @SuppressLint("SetTextI18n")
    override fun initUI(savedInstanceState: Bundle?) {
        binding.apply {
            this.model = this@MainActivity.model
        }

        binding.btn1.setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }

        var i = 1
        model._repos.observe(this) {
            binding.btn2.text = "${it.name} ${it.id + i++}"
        }

        model._liveRetrofit.observe(this) {
            when (it.requestStatus) {
                RequestStatus.START -> {
                }
                RequestStatus.SUCCESS -> {
                    it.data?.let {
                        binding.btn3.text = "${it.name} ${it.id + i++}"
                    }
                }
                RequestStatus.ERROR -> {
                    Log.i("csz", "error ${it.error?.message}")
                }
            }
        }
    }
}