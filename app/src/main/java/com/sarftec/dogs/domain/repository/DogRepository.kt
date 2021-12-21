package com.sarftec.dogs.domain.repository

import com.sarftec.dogs.domain.model.Breed
import com.sarftec.dogs.domain.model.Dog
import com.sarftec.dogs.tools.extra.Resource

interface DogRepository {
    suspend fun fetchDogs(breed: String) : Resource<List<Dog>>
    suspend fun getStaticRandomDog(breed: String) : Resource<Dog>
    suspend fun fetchDogBreeds() : Resource<List<Breed>>
}