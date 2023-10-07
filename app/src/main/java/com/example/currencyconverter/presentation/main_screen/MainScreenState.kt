package com.example.currencyconverter.presentation.main_screen

import com.example.currencyconverter.domain.model.CurrencyRate

data class MainScreenState(
    val fromCurrencyCode: String = "USD",
    val toCurrencyCode: String = "CAD",
    val fromCurrencyValue: String = "0.00",
    val toCurrencyValue: String = "0.00",
    val selection: SelectionState = SelectionState.FROM,
    val currencyRates: Map<String, CurrencyRate> = emptyMap(),
    val error: String? = null
)

enum class SelectionState {
    FROM,
    TO
}