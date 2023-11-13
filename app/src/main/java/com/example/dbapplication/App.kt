package com.example.dbapplication

import android.app.Application
import com.example.dbapplication.data.MainDB

class App : Application() {
    val database by lazy { MainDB.createDB(this) }
}