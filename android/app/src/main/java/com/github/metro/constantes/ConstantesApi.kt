package com.github.metro.constantes

class ConstantesApi {
    companion object {
        private val CAMINHO_BASE = "http://192.168.0.167:3000"
        val CAMINHO_LOCALIDADE = CAMINHO_BASE + "/localidade"
        val CAMINHO_STATION = CAMINHO_LOCALIDADE + "/station"
        val CAMINHO_SEARCH = CAMINHO_LOCALIDADE + "/search"
    }
}