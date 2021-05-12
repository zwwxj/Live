package com.my.live.activity

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.csz.permission.core.ResultCallback
import com.my.base.component.activity.BaseDataBindingActivity
import com.my.base.component.activity.TestActivity
import com.my.base.ext.navigateTo
import com.my.base.retrofit.RequestStatus
import com.my.live.R
import com.my.live.databinding.ActivityMainBinding
import com.my.live.viewmodel.LoginViewModel
import com.zs.base_library.common.requestPermissionsInner
import com.zs.base_library.common.startImagePicker
import com.zs.base_library.common.toast
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
            navigateTo<TestActivity>("a" to arrayOf(10, 20, 30))
        }

        binding.btnPick.setOnClickListener {
            startImagePicker(this)
        }

        binding.btnPermission.setOnClickListener {
            requestPermissionsInner(
                object : ResultCallback() {
                    override fun onSuccess() {
                        toast(" 权限请求成功")
                    }

                    override fun onFailure(permissions: Array<out String>?) {
                        permissions?.forEach { p ->
                            Log.i("csz", "per   $p")
                        }
                    }
                },
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)
        }


        model._repos.observe(this)
        {
            binding.btn2.text = "${it.name} ${it.id}"
        }

        Log.i(
            "csz",
            "model ${model.hashCode()}   ${model._liveRetrofit.hashCode()}   ${model._liveRetrofit.value}"
        )

        model._liveRetrofit.observe(this)
        {
            when (it.requestStatus) {
                RequestStatus.START -> {
                }
                RequestStatus.SUCCESS -> {
                    it.data?.let {
                        binding.btn3.text = "${it.name} ${it.id}"
                    }
                }
                RequestStatus.ERROR -> {
                    Log.i("csz", "error ${it.error?.errorMessage}")
                }
            }
        }

        model._Trans.observe(this)
        {
            when (it.requestStatus) {
                RequestStatus.START -> {
                }
                RequestStatus.SUCCESS -> {
                    it.data?.let {
                        binding.btn4.text = "${it.name} ${it.id}"
                    }
                }
                RequestStatus.ERROR -> {
                    Log.i("csz", "error ${it.error?.errorMessage}")
                }
            }
        }

        model._live4.observe(this)
        {
            binding.btn5.text = "${it.name} ${it.id}"
        }
        model._error.observe(this)
        {
            toast(it.errorMessage)
        }
    }

}