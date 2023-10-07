package com.example.currencyconverter.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencyRates")
data class CurrencyRateEntity(
    @PrimaryKey
    val code: String,
    val name: String,
    val rate: Double
)
