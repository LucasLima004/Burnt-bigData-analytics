package com.github.metro.adapter.db

import android.content.Context
import androidx.room.Room

class LocaleDatabaseProvider(private val context: Context) {
    val database: LocaleDatabase = Room.databaseBuilder(
        context,
        LocaleDatabase::class.java,
        "app_database"
    ).build()

    val favoriteLocaleDao = database.favoriteLocaleDao()
}