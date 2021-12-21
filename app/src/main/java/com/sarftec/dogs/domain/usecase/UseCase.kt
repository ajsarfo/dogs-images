package com.sarftec.dogs.domain.usecase

abstract class UseCase<in T : UseCase.Param, out U : UseCase.Response> {

    suspend operator fun invoke(param: T? = null): U {
        return execute(param)
    }

    protected abstract suspend fun execute(param: T? = null): U

    interface Param
    interface Response

    object EmptyParam : Param
}
