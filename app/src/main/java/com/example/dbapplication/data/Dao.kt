package com.example.dbapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(nameEntity: NameEntity)

    @Query("SELECT * FROM NameEntity")
    fun selectItems(): Flow<List<NameEntity>>

    @Delete
    fun deleteItem(nameEntity: NameEntity)
}