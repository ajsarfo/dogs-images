package com.sarftec.dogs.data.network.model

import kotlinx.serialization.Serializable

@Serializable
class DogList(
    val message: List<String>,
    val status: String
)