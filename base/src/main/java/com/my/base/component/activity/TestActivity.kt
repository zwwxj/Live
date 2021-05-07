package com.my.base.component.activity

import android.os.Bundle
import com.my.base.R
import com.my.base.component.fragment.TestFragment
import com.my.base.databinding.ActivityTestBinding

/**
 * 仅仅用于测试
 * @author caishuzhan
 */
class TestActivity : BaseViewBindingActivity<ActivityTestBinding>() {

    //BaseViewBindingActivity
     override fun bindContentView(): ActivityTestBinding {
         val bind = ActivityTestBinding.inflate(layoutInflater)
         return bind
     }

    override fun initUI(savedInstanceState: Bundle?) {
        supportFragmentManager.beginTransaction().add(R.id.layout,TestFragment()).commitAllowingStateLoss()
    }

}