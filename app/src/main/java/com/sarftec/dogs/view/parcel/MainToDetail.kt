package com.sarftec.dogs.view.parcel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class MainToDetail(
    val breed: String
) : Parcelable