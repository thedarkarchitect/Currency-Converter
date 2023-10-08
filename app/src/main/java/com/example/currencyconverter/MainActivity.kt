package com.example.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.presentation.main_screen.MainScreen
import com.example.currencyconverter.presentation.main_screen.MainScreenViewModel
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                val viewModel: MainScreenViewModel = hiltViewModel()
                Surface {
                    MainScreen(
                        state = viewModel.state,
                        onEvent = viewModel::onEvent
                    )
                }
            }
        }
    }
}

