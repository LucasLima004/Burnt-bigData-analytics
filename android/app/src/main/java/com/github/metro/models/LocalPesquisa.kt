package com.github.metro.models

import java.io.Serializable


data class LocalPesquisa(
    val nome: String,
    val lat: Double,
    val lon: Double,
    val endereco: String
): Serializable
