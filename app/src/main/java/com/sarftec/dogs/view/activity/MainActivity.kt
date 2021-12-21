package com.sarftec.dogs.view.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.sarftec.dogs.R
import com.sarftec.dogs.databinding.ActivityMainBinding
import com.sarftec.dogs.view.adapter.BreedAdapter
import com.sarftec.dogs.view.advertisement.AdCountManager
import com.sarftec.dogs.view.advertisement.BannerManager
import com.sarftec.dogs.view.parcel.MainToDetail
import com.sarftec.dogs.view.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val layoutBinding by lazy {
        ActivityMainBinding.inflate(
            layoutInflater
        )
    }

    private val viewModel by viewModels<MainViewModel>()

    private val breedAdapter by lazy {
        BreedAdapter(lifecycleScope, viewModel) {
          interstitialManager?.showAd {
              navigateToWithParcel(
                  DetailActivity::class.java,
                  parcel = MainToDetail(it.name)
              )
          }
        }
    }

    override fun canShowInterstitial(): Boolean = true

    override fun createAdCounterManager(): AdCountManager {
        return AdCountManager(listOf(1, 3, 4, 2))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutBinding.root)
        /*************** Admob Configuration ********************/
        BannerManager(this, adRequestBuilder).attachBannerAd(
            getString(R.string.admob_banner_main),
            layoutBinding.mainBanner
        )
        /**********************************************************/
        setupAdapter()
        viewModel.getDogBreeds()
        viewModel.screenState.observe(this) {
            observeState(it)
        }
        layoutBinding.networkError.reload.setOnClickListener {
            viewModel.getDogBreeds()
        }
    }

    private fun observeState(state: MainViewModel.ScreenState) {
        when (state) {
            is MainViewModel.ScreenState.DogBreeds -> {
                showRecyclerView(true)
                breedAdapter.submitItems(state.breeds)
            }
            is MainViewModel.ScreenState.Error -> {
                showNetworkError(true)
                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            }
            is MainViewModel.ScreenState.Loading -> {
                showLoadingProgress(true)
            }
        }
    }

    private fun showNetworkError(makeVisible: Boolean) {
        layoutBinding.apply {
            networkError.parent.visibility = if (makeVisible) View.VISIBLE else View.GONE
            recyclerView.visibility = if (!makeVisible) View.VISIBLE else View.GONE
            progressCircular.visibility = if (!makeVisible) View.VISIBLE else View.GONE
        }
    }

    private fun showLoadingProgress(makeVisible: Boolean) {
        layoutBinding.apply {
            networkError.parent.visibility = if (!makeVisible) View.VISIBLE else View.GONE
            recyclerView.visibility = if (!makeVisible) View.VISIBLE else View.GONE
            progressCircular.visibility = if (makeVisible) View.VISIBLE else View.GONE
        }
    }

    private fun showRecyclerView(makeVisible: Boolean) {
        layoutBinding.apply {
            networkError.parent.visibility = if (!makeVisible) View.VISIBLE else View.GONE
            recyclerView.visibility = if (makeVisible) View.VISIBLE else View.GONE
            progressCircular.visibility = if (!makeVisible) View.VISIBLE else View.GONE
        }
    }

    private fun setupAdapter() {
        layoutBinding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            setHasFixedSize(true)
            adapter = breedAdapter
        }
    }
}