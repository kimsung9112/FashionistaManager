package com.example.fashionistamanager.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fashionistamanager.data.dao.MainUrlDao
import com.example.fashionistamanager.data.entity.MainUrlEntity

@Database(entities = [MainUrlEntity::class], version = 1)
abstract class FashionistaDatabase : RoomDatabase() {

    abstract fun repositoryDao() : MainUrlDao
}