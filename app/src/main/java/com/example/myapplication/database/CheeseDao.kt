package com.example.myapplication.database

import androidx.room.*

@Dao
interface BirdsDao {
    @Query("SELECT * FROM birds WHERE name LIKE :name")
    fun findBirds(name: String): List<Birds>

    @Query("SELECT favorite FROM birds WHERE :id LIMIT 1")
    fun isFavorite(id: Long): Int

    @Update
    fun favoriteCheese(birds: Birds): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(birds:List<Birds>): List<Long>
}