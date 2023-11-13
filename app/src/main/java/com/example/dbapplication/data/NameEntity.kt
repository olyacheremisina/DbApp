package com.example.dbapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val text: String
)
