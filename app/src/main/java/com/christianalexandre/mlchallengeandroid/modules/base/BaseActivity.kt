package com.christianalexandre.mlchallengeandroid.modules.base

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.christianalexandre.mlchallengeandroid.databinding.ActivityHomeBinding
import com.google.android.material.appbar.MaterialToolbar

abstract class BaseActivity : AppCompatActivity() {

    // region Setup Methods
    fun setupTopBar(toolbar: MaterialToolbar, @StringRes title: Int) {
        setSupportActionBar(toolbar)

        this.title = getString(title)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
    // endregion
}