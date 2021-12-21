package com.sarftec.dogs.view.parcel

import com.sarftec.dogs.domain.model.Breed

fun MainToDetail.toBreed() : Breed {
    return Breed(breed)
}