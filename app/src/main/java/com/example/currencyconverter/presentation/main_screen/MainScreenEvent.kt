package com.example.currencyconverter.presentation.main_screen

sealed class MainScreenEvent {
    data object FromCurrencySelect: MainScreenEvent()
    data object ToCurrencySelect: MainScreenEvent()
    data object SwapIconCLicked: MainScreenEvent()
    data class BottomSheetItemClicked(val value: String): MainScreenEvent()//items on modalsheet
    data class NumberButtonClicked(val value: String): MainScreenEvent()//keyboard
}