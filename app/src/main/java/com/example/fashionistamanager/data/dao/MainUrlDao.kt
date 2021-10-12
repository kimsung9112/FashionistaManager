package com.example.fashionistamanager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fashionistamanager.data.entity.MainUrlEntity


@Dao
interface MainUrlDao {

    @Insert
    suspend fun insertAll(clothesList: List<MainUrlEntity>)



}