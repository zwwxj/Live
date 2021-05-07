package com.my.base.component.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.my.base.R
import com.my.base.databinding.ActivityTestBinding
import com.my.base.databinding.FragmentTestBinding

/**
 * 仅仅用于测试
 * @author caishuzhan
 */
class TestFragment:BaseDataBindingFragment<FragmentTestBinding>() {

   /* override fun bindContentView(inflater: LayoutInflater, container: ViewGroup?): FragmentTestBinding {
        return FragmentTestBinding.inflate(inflater,container,false)
    }*/

    override fun initUI(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        binding.tv.text = "databind fragment"
    }

    override fun getLayoutId(): Int = R.layout.fragment_test
}