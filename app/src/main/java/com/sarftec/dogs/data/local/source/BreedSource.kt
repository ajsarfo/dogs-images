package com.sarftec.dogs.data.local.source

import com.sarftec.dogs.data.local.mapper.toDomain
import com.sarftec.dogs.domain.model.Breed
import com.sarftec.dogs.tools.extra.Resource
import javax.inject.Inject

class BreedSource @Inject constructor() {
    private val breeds = listOf(
        "akita",
        "african",
        "beagle",
        "borzoi",
        "boxer",
        "chihuahua",
        "chow",
        "clumber",
        "affenpinscher"
    ).sorted()

    suspend fun getBreeds() : Resource<List<Breed>> {
        return Resource.success(
            breeds.map { it.toDomain() }
        )
    }
}