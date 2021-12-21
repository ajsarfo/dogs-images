package com.sarftec.dogs.data.network.mapper

import android.net.Uri
import com.sarftec.dogs.data.network.model.Dog
import com.sarftec.dogs.data.network.model.DogList

fun Dog.toDomain() : com.sarftec.dogs.domain.model.Dog {
    return com.sarftec.dogs.domain.model.Dog(Uri.parse(message))
}

fun DogList.toDomain() : List<com.sarftec.dogs.domain.model.Dog> {
    return message.map {
        com.sarftec.dogs.domain.model.Dog(Uri.parse(it))
    }
}