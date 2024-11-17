package com.github.metro.adapter.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.github.metro.models.FavoriteLocal

@Dao
interface FavoriteLocaleDao {

    @Insert
    suspend fun insertFavoriteLocale(favoriteLocale: FavoriteLocal)

    @Query("SELECT * FROM favorite_locale")
    suspend fun getAllFavoriteLocale(): List<FavoriteLocal>

    @Query("SELECT * FROM favorite_locale WHERE id = :id")
    suspend fun getFavoriteLocaleById(id: Int): FavoriteLocal?

    @Query("SELECT * FROM favorite_locale WHERE nome = :nome")
    suspend fun getFavoriteLocaleByName(nome: String): FavoriteLocal?

    @Delete
    suspend fun delete(favoriteLocal: FavoriteLocal)

}