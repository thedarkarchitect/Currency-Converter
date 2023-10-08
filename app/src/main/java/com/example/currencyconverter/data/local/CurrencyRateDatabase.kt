package com.example.currencyconverter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.currencyconverter.data.local.entity.CurrencyRateEntity

@Database(
    entities = [CurrencyRateEntity::class],
    version = 2,
    exportSchema = false
)
abstract class CurrencyRateDatabase : RoomDatabase() {
    abstract val currencyRateDao: CurrencyRateDao
}