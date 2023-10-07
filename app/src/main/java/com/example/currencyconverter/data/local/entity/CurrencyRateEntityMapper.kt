package com.example.currencyconverter.data.local.entity

import com.example.currencyconverter.domain.model.CurrencyRate

//this will convert the currencyRateEntity to currencyRate data class and vice versa

fun CurrencyRateEntity.toCurrencyRate(): CurrencyRate {
    return CurrencyRate(
        code = code,
        name = name,
        rate = rate
    )
}

fun CurrencyRate.toCurrencyRateEntity(): CurrencyRateEntity {
    return CurrencyRateEntity(
        code = code,
        name = name,
        rate = rate
    )
}