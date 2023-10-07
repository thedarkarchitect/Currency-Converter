package com.example.currencyconverter.presentation.main_screen


import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconverter.R
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.ExperimentalComposeUiApi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    state: MainScreenState,
    onEvent: (MainScreenEvent) -> Unit
) {
    val keys = listOf(
        "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "0", "C"
    )

    val context = LocalContext.current

    LaunchedEffect(key1 = state.error) {
        if(state.error != null) {
            Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
        }
    }

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var shouldBottomSheetShow by remember { mutableStateOf(false) }

    if(shouldBottomSheetShow) {
        ModalBottomSheet(
            onDismissRequest = { shouldBottomSheetShow = false }
        ){

        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            modifier = modifier.fillMaxWidth(),
            text = "CoinSwap",
            fontFamily = FontFamily.Cursive,
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Box(
            contentAlignment = Alignment.CenterStart
        ){
            Column {
                Card(
                    modifier = modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        CurrencyRow(
                            modifier = modifier.fillMaxWidth(),
                            currencyCode = state.fromCurrencyCode,
                            currencyName = state.currencyRates[state.fromCurrencyCode]?.name ?: "",
                            onDropDownIconClicked = {}
                        )
                        Text(
                            text = state.fromCurrencyValue,
                            fontSize = 40.sp,
                            modifier = modifier.clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                                onClick = { onEvent(MainScreenEvent.FromCurrencySelect) }
                            ),
                            color = if(state.selection == SelectionState.FROM) {
                                MaterialTheme.colorScheme.primary
                            } else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                Spacer(modifier = modifier.height(12.dp))
                Card(
                    modifier = modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = state.toCurrencyValue,
                            fontSize = 40.sp,
                            modifier = modifier.clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                                onClick = { onEvent(MainScreenEvent.ToCurrencySelect) }
                            ),
                            color = if(state.selection == SelectionState.TO) {
                                MaterialTheme.colorScheme.primary
                            } else MaterialTheme.colorScheme.onSurface
                        )
                        CurrencyRow(
                            modifier = modifier.fillMaxWidth(),
                            currencyCode = state.toCurrencyCode,
                            currencyName = state.currencyRates[state.toCurrencyCode]?.name ?: "",
                            onDropDownIconClicked = {}
                        )
                    }
                }
            }
            Box(
                modifier = modifier
                    .padding(start = 40.dp)
                    .clip(CircleShape)
                    .clickable {
                        onEvent(MainScreenEvent.SwapIconCLicked)
                    }
                    .background(color = MaterialTheme.colorScheme.background)
            ){
                Icon(
                    painter = painterResource(id = R.drawable.sync_icon),
                    contentDescription = "Swap Currency",
                    modifier = modifier
                        .padding(8.dp)
                        .size(25.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        LazyVerticalGrid(
            modifier = modifier.padding(horizontal = 35.dp),
            columns = GridCells.Fixed(3)
        ){
            items( keys ) {key ->
                KeyboardButton(
                    modifier = modifier.aspectRatio(1f),
                    key = key,
                    backgroundColor = if (key == "C") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    onClick = {
                        //action to recieve clicked key value
                        onEvent(MainScreenEvent.NumberButtonClicked(key))
                    }
                )
            }
        }
    }
}

@Composable
fun CurrencyRow(
    modifier : Modifier = Modifier,
    currencyCode: String,
    currencyName: String,
    onDropDownIconClicked: () -> Unit
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = currencyCode,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        IconButton( onClick = onDropDownIconClicked){
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Open Bottom Sheet"
            )
        }
        Spacer(modifier = modifier.weight(1f))
        Text(
            text = currencyName, fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun KeyboardButton(
    modifier: Modifier = Modifier,
    key: String,
    backgroundColor: Color,
    onClick: (String) -> Unit
){
    Box(
        modifier = modifier
            .padding(8.dp)
            .clip(CircleShape)
            .background(color = backgroundColor)
            .clickable { onClick(key) },
        contentAlignment = Alignment.Center
    ){
        Text(text = key, fontSize = 32.sp)
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MainScreen(
//
//    )
//}