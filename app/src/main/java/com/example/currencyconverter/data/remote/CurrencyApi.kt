package com.example.currencyconverter.data.remote

import com.example.currencyconverter.data.remote.dto.CurrencyDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("vi/latest")
    suspend fun getLatestRates(
        @Query("apikey") apiKey: String = API_KEY
    ): CurrencyDto

    companion object {
        const val API_KEY = "fca_live_hucd4A3NzSNT0iLBfQEUWIXlyp5FN4BOnkHG2Ej5"
        const val BASE_URL = "https://app.freecurrencyapi.com/"
    }
}