package com.sarftec.dogs.view.viewmodel

import androidx.lifecycle.*
import com.sarftec.dogs.domain.model.Breed
import com.sarftec.dogs.domain.model.Dog
import com.sarftec.dogs.domain.usecase.GetDogs
import com.sarftec.dogs.view.parcel.MainToDetail
import com.sarftec.dogs.view.parcel.toBreed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    private val getDogs: GetDogs
) : ViewModel() {

    private val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState>
        get() = _screenState

    fun getAtPosition(position: Int) : Dog? {
        val state = _screenState.value ?: return null
        return if(state is ScreenState.Dogs) state.dogs[position] else null
    }

    fun fetchDogs() {
        val parcel = stateHandle.get<MainToDetail>(PARCEL) ?: let {
            _screenState.value = ScreenState.Error("Error => MainToDetail parcel missing!")
            return
        }
        _screenState.value = ScreenState.Loading
        viewModelScope.launch {
            getDogs(GetDogs.GetParam(parcel.toBreed())).result.let {
                _screenState.value = if(it.isError()) ScreenState.Error(it.message!!)
                else ScreenState.Dogs(it.data!!.shuffled())
            }
        }
    }

    fun getBreed() : Breed? {
        return stateHandle.get<MainToDetail>(PARCEL)?.toBreed()
    }

    fun setParcel(parcel: MainToDetail) {
        stateHandle.set(PARCEL, parcel)
    }

    companion object {
        const val PARCEL = "main_to_detail_parcel"
    }

    sealed class ScreenState() {
        object Loading : ScreenState()
        class Dogs(val dogs: List<Dog>) : ScreenState()
        class Error(val message: String) : ScreenState()
    }
}