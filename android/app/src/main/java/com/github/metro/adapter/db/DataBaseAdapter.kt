package com.github.metro.adapter.db

import com.github.metro.models.FavoriteLocal
import com.github.metro.port.DataBasePort
import com.github.metro.utils.exception.FavoriteLocaleNotFoundException

class DataBaseAdapter(private val favoriteLocaleDao: FavoriteLocaleDao) : DataBasePort {
    override suspend fun insertFavoriteLocal(favoriteLocal: FavoriteLocal) {
        favoriteLocaleDao.getFavoriteLocaleLonLat(favoriteLocal.lat, favoriteLocal.lon)
            .isEmpty().apply { favoriteLocaleDao.insertFavoriteLocale(favoriteLocal) }
    }

    override suspend fun getAllFavoriteLocale(): List<FavoriteLocal> {
        return favoriteLocaleDao.getAllFavoriteLocale() ?: emptyList()
    }

    override suspend fun getFavoriteLocaleById(id: Int): FavoriteLocal? {
        return favoriteLocaleDao.getFavoriteLocaleById(id)?: throw FavoriteLocaleNotFoundException("Favorito com id $id não encontrado.")
    }

    override suspend fun getFavoriteLocaleByName(nome: String): FavoriteLocal? {
        return favoriteLocaleDao.getFavoriteLocaleByName(nome)?: throw FavoriteLocaleNotFoundException("Favorito com id $nome não encontrado.")
    }

    override suspend fun delete(favoriteLocal: FavoriteLocal) {
        favoriteLocaleDao.delete(favoriteLocal)
    }
}