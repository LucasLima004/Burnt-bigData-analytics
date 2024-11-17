package com.github.metro.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_locale")
data class FavoriteLocal (
    @PrimaryKey(autoGenerate = true) val id: Long  = 0,
    val nome: String,
    val lat: Double,
    val lon: Double,
    val endereco: String
)