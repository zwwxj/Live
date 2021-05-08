package com.my.base.component.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.my.base.R
import com.my.base.component.fragment.TestFragment
import com.my.base.databinding.ActivityTestBinding

/**
 * 仅仅用于测试
 * @author caishuzhan
 */
class TestActivity : BaseViewBindingActivity<ActivityTestBinding>() {

    override fun onHandleIntent(intent: Intent) {
        val t = intent.getSerializableExtra("a") as Array<*>
        Log.i("csz","t   ${t[0]}")
    }

    //BaseViewBindingActivity
     override fun bindContentView(): ActivityTestBinding {
         val bind = ActivityTestBinding.inflate(layoutInflater)
         return bind
     }

    override fun initUI(savedInstanceState: Bundle?) {
        supportFragmentManager.beginTransaction().add(R.id.layout,TestFragment()).commitAllowingStateLoss()
    }

}