package com.sarftec.dogs.view.activity

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.sarftec.dogs.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private val layoutBinding by lazy {
        ActivitySplashBinding.inflate(
            LayoutInflater.from(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutBinding.root)
        setSplashImage()
        lifecycleScope.launchWhenCreated {
            delay(1500)
            navigateTo(MainActivity::class.java, finish = true)
        }
    }

    private fun setSplashImage() {
        val imageName = "splash_icon.png"
        assets.open(imageName).use {
            layoutBinding.splashImageView.setImageBitmap(
                BitmapFactory.decodeStream(it)
            )
        }
    }
}