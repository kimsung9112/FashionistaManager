package com.example.fashionistamanager.data.database

import android.content.Context
import androidx.room.Room
import com.example.fashionistamanager.data.database.FashionistaDatabase

object DataBaseProvider {

    private const val DB_NAME = "fashionista_app.db"

    fun provideDB(applicationContext: Context) = Room.databaseBuilder(
        applicationContext,
        FashionistaDatabase::class.java, DB_NAME
    ).build()

}