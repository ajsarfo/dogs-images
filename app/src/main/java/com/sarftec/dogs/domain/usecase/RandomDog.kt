package com.sarftec.dogs.domain.usecase

import com.sarftec.dogs.domain.model.Breed
import com.sarftec.dogs.domain.model.Dog
import com.sarftec.dogs.domain.repository.DogRepository
import com.sarftec.dogs.tools.extra.Resource
import javax.inject.Inject

class RandomDog @Inject constructor(
    private val repository: DogRepository
) : UseCase<RandomDog.RandomParam, RandomDog.RandomResult>() {

    override suspend fun execute(param: RandomParam?): RandomResult {
        if (param == null) return RandomResult(Resource.error("Error => Random Param NULL!"))
        return RandomResult(
            repository.getStaticRandomDog(param.breed.name)
        )
    }

    class RandomParam(val breed: Breed) : Param
    class RandomResult(val result: Resource<Dog>) : Response


}