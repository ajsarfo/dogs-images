package com.sarftec.dogs.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarftec.dogs.domain.model.Breed
import com.sarftec.dogs.domain.model.Dog
import com.sarftec.dogs.domain.usecase.GetBreeds
import com.sarftec.dogs.domain.usecase.RandomDog
import com.sarftec.dogs.tools.extra.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBreeds: GetBreeds,
    private val randomDog: RandomDog
) : ViewModel() {

    private val _screenSate = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState>
        get() = _screenSate

    fun getDogBreeds() {
        _screenSate.value = ScreenState.Loading
        viewModelScope.launch {
            _screenSate.value = getBreeds().result.let {
                if (it.isError()) ScreenState.Error(it.message!!)
                else ScreenState.DogBreeds(it.data!!)
            }
        }
    }

    suspend fun getRandomDog(breed: Breed): Resource<Dog> {
        return randomDog(
            RandomDog.RandomParam(breed)
        ).result
    }

    sealed class ScreenState() {
        object Loading : ScreenState()
        class DogBreeds(val breeds: List<Breed>) : ScreenState()
        class Error(val message: String) : ScreenState()
    }
}