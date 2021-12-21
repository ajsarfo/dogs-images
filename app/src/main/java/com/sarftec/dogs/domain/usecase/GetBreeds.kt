package com.sarftec.dogs.domain.usecase

import com.sarftec.dogs.domain.model.Breed
import com.sarftec.dogs.domain.repository.DogRepository
import com.sarftec.dogs.tools.extra.Resource
import javax.inject.Inject

class GetBreeds @Inject constructor(
    private val repository: DogRepository
): UseCase<UseCase.EmptyParam, GetBreeds.GetResult>() {

    override suspend fun execute(param: EmptyParam?): GetResult {
        return GetResult(
           repository.fetchDogBreeds()
        )
    }

    class GetResult(val result: Resource<List<Breed>>) : Response
}