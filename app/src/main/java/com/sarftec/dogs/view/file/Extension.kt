package com.sarftec.dogs.view.file

import com.sarftec.dogs.domain.model.Breed
import java.util.*

fun String.capitalizeWords(): String {
    return this.split(" ")
        .joinToString(" ") {
            it.replaceFirstChar { char -> if (char.isLowerCase()) char.titlecase(Locale.getDefault()) else it }
        }.trimEnd()
}

fun Breed.parseName(): String {
    return this.name.substringBefore("/")
        .trim()
        .capitalizeWords()
}