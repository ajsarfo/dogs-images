package com.sarftec.dogs.domain.usecase

import com.sarftec.dogs.domain.model.Breed
import com.sarftec.dogs.domain.model.Dog
import com.sarftec.dogs.domain.repository.DogRepository
import com.sarftec.dogs.tools.extra.Resource
import javax.inject.Inject

class GetDogs @Inject constructor(
    private val repository: DogRepository
) : UseCase<GetDogs.GetParam, GetDogs.GetResult>() {

    override suspend fun execute(param: GetParam?): GetResult {
        if (param == null) return GetResult(Resource.error("Error =>  Get Dogs Param NULL!"))
        return GetResult(
            repository.fetchDogs(param.param.name)
        )
    }

    class GetParam(val param: Breed) : Param
    class GetResult(val result: Resource<List<Dog>>) : Response

}