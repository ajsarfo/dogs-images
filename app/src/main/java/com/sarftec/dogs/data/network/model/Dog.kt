package com.sarftec.dogs.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Dog(
    val message: String,
    val status: String
)