package com.christianalexandre.mlchallengeandroid.modules.home

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.christianalexandre.mlchallengeandroid.domain.repository.SearchRepositoryImpl
import com.christianalexandre.mlchallengeandroid.modules.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    @Inject
    lateinit var searchRepositoryImpl: SearchRepositoryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            searchRepositoryImpl.search("iphone")
        }
    }
}