package com.example.wavewash.presentation.orders

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wavewash.R
import com.example.wavewash.presentation.helpers.common.Calendar
import com.example.wavewash.presentation.helpers.common.ScreenHeaders
import com.example.wavewash.presentation.orders.orders_screen.components.*
import com.example.wavewash.presentation.helpers.popups.CalendarDialog
import com.example.wavewash.presentation.orders.orders_screen.OrdersEvent
import com.example.wavewash.presentation.orders.orders_screen.OrdersViewModel
import com.example.wavewash.presentation.services.services_screen.ServiceEvent
import com.example.wavewash.ui.theme.HeaderButtonStroke
import com.example.wavewash.ui.theme.QuantityOfServices
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.ui.theme.SupportAnswerBorder
import com.example.wavewash.utils.*

private const val TAG = "OrdersScreen"

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrdersScreen(
    navController: NavController,
    viewModel: OrdersViewModel = hiltViewModel()
) {
    val openDialogCustom = remember { mutableStateOf(false) }
    val state = viewModel.state
    
    if (openDialogCustom.value) {
        CalendarDialog(
            openDialogCustom = openDialogCustom,
            dateRangeSelected = { dateFrom, dateTo ->
                viewModel.onTriggerEvent(OrdersEvent.ChangeDates(dateFrom, dateTo))
            }
        )
    }

    Column {
        Calendar(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
                .clip(Shapes.small)
                .background(Color.White),
            beginDate = state.calendarDateFrom,
            endDate = state.calendarDateTo,
            onCalendarPopup = {
                openDialogCustom.value = true
            },
            onNextClick = {
                viewModel.onTriggerEvent(OrdersEvent.OnNextDateClick)
            },
            onPreviousClick = {
                viewModel.onTriggerEvent(OrdersEvent.OnPreviousDateClick)
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .clip(Shapes.large)
                .background(Color.White)
                .padding(16.dp, 24.dp)
        ) {

            item {
                Column {
                    ScreenHeaders(
                        isVisible = state.isVisibleTabs,
                        headers = headerOrderButtons,
                        selectedOption = ComposeString.resource(R.string.add_order).value(),
                        onClick = { index ->
                            when (index) {
                                0 -> navController.navigate(Screen.NewOrderScreenRoute.route + "/${-1L}")
                                1 -> Log.d(TAG, "OrdersScreen: Export")//TODO
                            }
                        }
                    )

                    OrdersTab(
                        isVisibleTab = state.isVisibleTabs,
                        isActive = state.isActive,
                        onClick = { index ->
                            if (index == 0) {
                                viewModel.onTriggerEvent(OrdersEvent.ActiveOrders)
                            } else {
                                viewModel.onTriggerEvent(OrdersEvent.FinishedOrders)
                            }
                        }
                    )
                }
            }

            stickyHeader {
                HeadersOfList()
            }

            items(state.orders.size) { index ->

                if (index >= state.orders.size - 1 && !state.endReached && !state.isLoading) {
                    viewModel.onTriggerEvent(OrdersEvent.GetOrders)
                }
                OrderItem(
                    order = state.orders[index],
                    onClick = {
                        navController.navigate(Screen.OrderDetailsScreenRoute.route + "/${state.orders[index].id}")
                    }
                )
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = HeaderButtonStroke
                )
            }
        }
    }

    if(state.isLoading && state.endReached.not()){
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            CircularProgressIndicator(
                color = SupportAnswerBorder
            )
        }
    }
    if(state.isLoading.not() && state.endReached && state.orders.isEmpty()){
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(text = "Пусто")
        }
    }
}

