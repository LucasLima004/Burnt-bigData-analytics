package com.github.metro.adapter.db
import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.metro.models.FavoriteLocal

@Database(entities = [FavoriteLocal::class], version = 1)
abstract class LocaleDatabase : RoomDatabase() {
    abstract fun favoriteLocaleDao(): FavoriteLocaleDao
}