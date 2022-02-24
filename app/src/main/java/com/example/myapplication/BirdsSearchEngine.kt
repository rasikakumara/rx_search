package com.example.myapplication

import android.content.Context
import android.util.Log
import com.example.myapplication.database.Birds
import com.example.myapplication.database.BirdsDataBase

class BirdsSearchEngine(private val context: Context) {

    fun search(query: String): List<Birds>? {
        Thread.sleep(2000)
        Log.d("Searching", "Searching for $query")
        return BirdsDataBase.getInstance(context).birdsDao().findBirds("%$query%")
    }

}