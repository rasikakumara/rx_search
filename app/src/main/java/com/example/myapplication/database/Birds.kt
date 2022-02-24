package com.example.myapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "birds", indices = [Index(value = ["name"], unique = true)])
data class Birds (@PrimaryKey(autoGenerate = true) var id: Long = 0,
             @ColumnInfo(name = "name") var name: String,
             @ColumnInfo(name = "favorite") var favorite: Int = 0)