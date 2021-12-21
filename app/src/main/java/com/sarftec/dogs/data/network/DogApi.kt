package com.sarftec.dogs.data.network

import com.sarftec.dogs.data.network.mapper.toDomain
import com.sarftec.dogs.data.network.service.DogService
import com.sarftec.dogs.domain.model.Dog
import com.sarftec.dogs.tools.extra.Resource
import retrofit2.Response
import javax.inject.Inject

class DogApi @Inject constructor(
    private val dogService: DogService,
) {
    suspend fun getRandomDog(breed: String): Resource<Dog> {
        return mapToResource(
            retrieve = { dogService.getRandomDog(breed) },
            mapper = { it.toDomain() }
        )
    }

    suspend fun getDogsForBreed(breed: String): Resource<List<Dog>> {
        return mapToResource(
            retrieve = { dogService.getDogsForBreed(breed) },
            mapper = { it.toDomain() }
        )
    }

    private suspend fun <T, U> mapToResource(
        retrieve: suspend () -> Response<T>,
        mapper: (T) -> U
    ): Resource<U> {
        return try {
            val response = retrieve()
            if (!response.isSuccessful) {
                return Resource.error("Error => ${response.errorBody()?.string()}")
            }
            Resource.success(mapper(response.body()!!))
        } catch (e: Exception) {
            Resource.error("Error => ${e.message}")
        }
    }
}