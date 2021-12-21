package com.sarftec.dogs.view.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.sarftec.dogs.R
import com.sarftec.dogs.databinding.ActivityDetailBinding
import com.sarftec.dogs.view.adapter.DogAdapter
import com.sarftec.dogs.view.advertisement.BannerManager
import com.sarftec.dogs.view.advertisement.RewardVideoManager
import com.sarftec.dogs.view.dialog.LoadingDialog
import com.sarftec.dogs.view.dialog.WallpaperDialog
import com.sarftec.dogs.view.file.downloadGlideImage
import com.sarftec.dogs.view.file.toast
import com.sarftec.dogs.view.handler.ReadWriteHandler
import com.sarftec.dogs.view.handler.ToolingHandler
import com.sarftec.dogs.view.parcel.MainToDetail
import com.sarftec.dogs.view.utils.ZoomOutPageTransformer
import com.sarftec.dogs.view.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class DetailActivity : BaseActivity() {

    private val layoutBinding by lazy {
        ActivityDetailBinding.inflate(
            layoutInflater
        )
    }

    private val viewModel by viewModels<DetailViewModel>()

    private val dogAdapter by lazy {
        DogAdapter(viewModel)
    }

    private val loadingDialog by lazy {
        LoadingDialog(this, layoutBinding.root)
    }

    private val wallpaperDialog by lazy {
        WallpaperDialog(
            layoutBinding.root,
            onHome = {
                runCurrentBitmapCallback {
                    toolingHandler.setWallpaper(it, ToolingHandler.WallpaperOption.HOME)
                }
            },
            onLock = {
                runCurrentBitmapCallback {
                    toolingHandler.setWallpaper(it, ToolingHandler.WallpaperOption.LOCK)
                }
            }
        )
    }

    private lateinit var toolingHandler: ToolingHandler

    private val rewardVideoManager by lazy {
        RewardVideoManager(
            this,
            R.string.admob_reward_video_id,
            adRequestBuilder,
            networkManager
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutBinding.root)
        /*************** Admob Configuration ********************/
        BannerManager(this, adRequestBuilder).attachBannerAd(
            getString(R.string.admob_banner_detail),
            layoutBinding.mainBanner
        )
        /**********************************************************/
        toolingHandler = ToolingHandler(this, ReadWriteHandler(this))
        getParcelFromIntent<MainToDetail>(intent)?.let {
            viewModel.setParcel(it)
        }
        viewModel.fetchDogs()
        setupToolbar()
        setupAdapter()
        setupButtonListeners()
        viewModel.screenState.observe(this) {
            observeState(it)
        }
    }

    private fun observeState(state: DetailViewModel.ScreenState) {
        when (state) {
            is DetailViewModel.ScreenState.Loading -> showLoading(true)
            is DetailViewModel.ScreenState.Error -> {
                showError(true)
                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            }
            is DetailViewModel.ScreenState.Dogs -> {
                showLayout(true)
                showNoDogsMessage(state.dogs.isEmpty())
                dogAdapter.submitItems(state.dogs)
            }
        }
    }

    private fun setupToolbar() {
        layoutBinding.materialToolbar.setNavigationOnClickListener { onBackPressed() }
        val name = viewModel.getBreed()?.name?.let { name ->
            name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.ENGLISH) else it.toString()
            }
        }
        layoutBinding.materialToolbar.title = name?.let {
            getString(R.string.detail_toolbar_title, it)
        }
    }

    private fun runCurrentBitmapCallback(callback: (Bitmap) -> Unit) {
        loadingDialog.show()
        lifecycleScope.launch {
            val result = viewModel.getAtPosition(layoutBinding.viewPager.currentItem)?.let {
                this@DetailActivity.downloadGlideImage(it.image)
            }
            if(result == null || result.isError()) {
                loadingDialog.dismiss()
                toast("Action Failed!")
                return@launch
            }
           rewardVideoManager.showRewardVideo {
               loadingDialog.dismiss()
               callback(result.data!!)
           }
        }
    }

    private fun setupButtonListeners() {
        layoutBinding.share.setOnClickListener {
            runCurrentBitmapCallback { toolingHandler.shareImage(it) }
        }
        layoutBinding.download.setOnClickListener {
            runCurrentBitmapCallback { toolingHandler.saveImage(it) }
        }
        layoutBinding.wallpaper.setOnClickListener {
            runCurrentBitmapCallback { wallpaperDialog.show() }
        }
    }

    private fun setupAdapter() {
        layoutBinding.viewPager.adapter = dogAdapter
        layoutBinding.viewPager.setPageTransformer(
            ZoomOutPageTransformer()
        )
    }

    private fun showNoDogsMessage(makeVisible: Boolean) {
        layoutBinding.iconsHolder.visibility = if (!makeVisible) View.VISIBLE else View.GONE
    }

    private fun showLoading(makeVisible: Boolean) {
        layoutBinding.apply {
            contentLayout.visibility = if (!makeVisible) View.VISIBLE else View.GONE
            circularProgress.visibility = if (makeVisible) View.VISIBLE else View.GONE
        }
    }

    private fun showLayout(makeVisible: Boolean) {
        layoutBinding.apply {
            contentLayout.visibility = if (makeVisible) View.VISIBLE else View.GONE
            circularProgress.visibility = if (!makeVisible) View.VISIBLE else View.GONE
        }
    }

    private fun showError(makeVisible: Boolean) {
        layoutBinding.apply {
            contentLayout.visibility = if (!makeVisible) View.VISIBLE else View.GONE
            circularProgress.visibility = if (!makeVisible) View.VISIBLE else View.GONE
        }
    }
}