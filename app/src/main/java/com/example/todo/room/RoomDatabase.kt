package com.example.todo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room

@Database(entities = [Entity::class], version = 1)
abstract class RoomDatabase : androidx.room.RoomDatabase() {

    abstract fun getDao(): DAO


    companion object {

        @Volatile
        private var INSTANCE: RoomDatabase? = null

        fun getInstance(context: Context): RoomDatabase {
            var instance = INSTANCE
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDatabase::class.java,
                    "room_database"
                ).build()
                INSTANCE = instance
            }
            return instance

        }


    }


}