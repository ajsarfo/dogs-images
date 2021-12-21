package com.sarftec.dogs.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarftec.dogs.data.local.room.ROOM_DOG_TABLE
import com.sarftec.dogs.data.local.room.model.RoomDog

@Dao
interface RoomDogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDog(dog: RoomDog)

    @Query("select * from $ROOM_DOG_TABLE where breed = :breed")
    suspend fun getDog(breed: String) : RoomDog?

    @Query("delete from $ROOM_DOG_TABLE")
    suspend fun clearDogs()
}