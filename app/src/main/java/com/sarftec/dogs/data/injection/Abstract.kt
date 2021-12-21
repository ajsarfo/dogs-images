package com.sarftec.dogs.data.injection

import com.sarftec.dogs.data.repository.DogRepositoryImpl
import com.sarftec.dogs.domain.repository.DogRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface Abstract {

    @Binds
    fun dogRepository(repository: DogRepositoryImpl) : DogRepository
}