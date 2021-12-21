package com.sarftec.dogs.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sarftec.dogs.data.local.room.dao.RoomDogDao
import com.sarftec.dogs.data.local.room.model.RoomDog

@Database(
    entities = [RoomDog::class],
    version = 1,
    exportSchema = false
)
abstract class DogDatabase : RoomDatabase() {
    abstract fun roomDogDao() : RoomDogDao

    companion object {
        fun getInstance(context: Context) : DogDatabase {
            return Room.databaseBuilder(
                context,
                DogDatabase::class.java,
                "dog_database.db"
            ).build()
        }
    }
}