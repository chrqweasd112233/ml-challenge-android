package com.christianalexandre.mlchallengeandroid.modules.main

import android.content.Intent
import android.os.Bundle
import com.christianalexandre.mlchallengeandroid.modules.base.BaseActivity
import com.christianalexandre.mlchallengeandroid.modules.home.HomeActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
}