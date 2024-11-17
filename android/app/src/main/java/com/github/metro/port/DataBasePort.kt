package com.github.metro.port

import com.github.metro.models.FavoriteLocal

interface DataBasePort {

    suspend fun insertFavoriteLocal(favoriteLocal: FavoriteLocal)

    suspend fun getAllFavoriteLocale(): List<FavoriteLocal>

    suspend fun getFavoriteLocaleById(id: Int): FavoriteLocal?

    suspend fun getFavoriteLocaleByName(nome: String): FavoriteLocal?

    suspend fun delete(favoriteLocal: FavoriteLocal)
}

