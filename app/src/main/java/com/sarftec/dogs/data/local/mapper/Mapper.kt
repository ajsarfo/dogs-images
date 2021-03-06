package com.sarftec.dogs.data.local.mapper

import android.net.Uri
import com.sarftec.dogs.data.local.room.model.RoomDog
import com.sarftec.dogs.domain.model.Breed
import com.sarftec.dogs.domain.model.Dog
import java.util.*

fun String.toDomain() : Breed {
    return Breed(this.lowercase(Locale.ENGLISH))
}

fun Dog.toRoom(breed: String) : RoomDog {
    return RoomDog(breed, image.toString())
}

fun RoomDog.toDomain() : Dog {
    return Dog(Uri.parse(image))
}