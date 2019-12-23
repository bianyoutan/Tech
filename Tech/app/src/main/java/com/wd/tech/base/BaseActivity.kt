package com.wd.tech.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initLayout())
        initData();
        initMethod();
    }

    abstract fun initLayout(): Int

    abstract fun initData()

    abstract fun initMethod()
}
