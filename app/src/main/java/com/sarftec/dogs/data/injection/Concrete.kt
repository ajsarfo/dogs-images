package com.sarftec.dogs.data.injection

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sarftec.dogs.data.local.room.DogDatabase
import com.sarftec.dogs.data.network.service.DogService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Concrete {

    @Provides
    fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .build()
    }

    @Provides
    fun dogService(retrofit: Retrofit) : DogService {
        return DogService.create(retrofit)
    }

    @Singleton
    @Provides
    fun dogDatabase(@ApplicationContext context: Context) : DogDatabase {
        return DogDatabase.getInstance(context)
    }
}