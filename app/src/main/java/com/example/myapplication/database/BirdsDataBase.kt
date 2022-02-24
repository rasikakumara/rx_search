package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Birds::class)], version = 1)
abstract class BirdsDataBase: RoomDatabase() {
    abstract fun birdsDao(): BirdsDao

    companion object {

        private var INSTANCE: BirdsDataBase? = null

        fun getInstance(context: Context): BirdsDataBase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(context,
                        BirdsDataBase::class.java, "birds.db")
                        .build()
                }
            }
            return INSTANCE as BirdsDataBase
        }

        fun destroyInstance() {
            INSTANCE = null
        }

    }
}