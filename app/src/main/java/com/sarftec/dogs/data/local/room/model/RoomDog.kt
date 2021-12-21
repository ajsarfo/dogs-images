package com.sarftec.dogs.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sarftec.dogs.data.local.room.ROOM_DOG_TABLE

@Entity(tableName = ROOM_DOG_TABLE)
data class RoomDog(
    @PrimaryKey(autoGenerate = false)
    val breed: String,
    val image: String
)