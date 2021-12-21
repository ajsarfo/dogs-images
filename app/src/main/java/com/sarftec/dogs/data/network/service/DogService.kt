package com.sarftec.dogs.data.network.service

import com.sarftec.dogs.data.network.model.Dog
import com.sarftec.dogs.data.network.model.DogList
import com.sarftec.dogs.domain.model.Breed
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DogService {

    @GET("breed/{breed}/images/random")
    suspend fun getRandomDog(@Path("breed") breed: String) : Response<Dog>

    @GET("breed/{breed}/images/random/50")
    suspend fun getDogsForBreed(@Path("breed") breed: String) : Response<DogList>

    companion object {
        fun create(retrofit: Retrofit) : DogService {
            return retrofit.create(DogService::class.java)
        }
    }
}