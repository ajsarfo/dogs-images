package com.sarftec.dogs.data.repository

import com.sarftec.dogs.data.local.mapper.toDomain
import com.sarftec.dogs.data.local.mapper.toRoom
import com.sarftec.dogs.data.local.room.DogDatabase
import com.sarftec.dogs.data.local.source.BreedSource
import com.sarftec.dogs.data.network.DogApi
import com.sarftec.dogs.domain.model.Breed
import com.sarftec.dogs.domain.model.Dog
import com.sarftec.dogs.domain.repository.DogRepository
import com.sarftec.dogs.tools.extra.Resource
import javax.inject.Inject

class DogRepositoryImpl @Inject constructor(
    private val dogApi: DogApi,
    private val breedSource: BreedSource,
    private val dogDatabase: DogDatabase
): DogRepository {

    override suspend fun fetchDogs(breed: String): Resource<List<Dog>> {
        return dogApi.getDogsForBreed(breed.toApiBreed())
        //return Resource.error("Error => Fetch Dogs not yet implemented!")
    }

    override suspend fun getStaticRandomDog(breed: String): Resource<Dog> {
       val dog = dogDatabase.roomDogDao().getDog(breed)?.toDomain()
        if(dog != null) return Resource.success(dog)
        return dogApi.getRandomDog(breed.toApiBreed()).also {
            if(it.isSuccess()) dogDatabase.roomDogDao().insertDog(
                it.data!!.toRoom(breed)
            )
        }
        //return Resource.error("Error => Get Static Random Dog not yet implemented!")
    }

    override suspend fun fetchDogBreeds(): Resource<List<Breed>> {
        return breedSource.getBreeds()
        //return Resource.error("Error => Fetch Dog Breeds not yet implemented!")
    }

    private fun String.toApiBreed() : String {
        return this.substringAfterLast("/").trim()
    }
}