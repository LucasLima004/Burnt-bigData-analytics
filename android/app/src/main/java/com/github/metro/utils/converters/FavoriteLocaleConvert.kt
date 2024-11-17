package com.github.metro.utils.converters

import com.github.metro.models.FavoriteLocal
import com.github.metro.models.LocalPesquisa

class FavoriteLocaleConvert {
    fun toFavoriteLocal(local: LocalPesquisa): FavoriteLocal {
        return FavoriteLocal(
            nome = local.nome,
            lat = local.lat,
            lon = local.lon,
            endereco = local.endereco
        )
    }

    fun toLocalPesquisa(local: FavoriteLocal): LocalPesquisa {
        return LocalPesquisa(
            nome = local.nome,
            lat = local.lat,
            lon = local.lon,
            endereco = local.endereco
        )
    }
}