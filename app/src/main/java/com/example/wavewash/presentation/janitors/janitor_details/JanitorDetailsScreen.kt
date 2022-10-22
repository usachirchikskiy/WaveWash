package com.example.wavewash.presentation.janitors.janitor_details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wavewash.presentation.helpers.common.BackButton
import com.example.wavewash.presentation.helpers.common.Calendar
import com.example.wavewash.presentation.helpers.common.ScreenHeaders
import com.example.wavewash.presentation.helpers.popups.CalendarDialog
import com.example.wavewash.presentation.janitors.janitor_details.components.CardsJanitorAnalytics
import com.example.wavewash.presentation.janitors.janitor_details.components.CardsJanitorInfo
import com.example.wavewash.presentation.orders.orders_screen.OrdersEvent
import com.example.wavewash.presentation.orders.orders_screen.components.HeadersOfList
import com.example.wavewash.presentation.orders.orders_screen.components.OrderItem
import com.example.wavewash.presentation.orders.orders_screen.components.OrdersTab
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.utils.REFRESH_ORDER
import com.example.wavewash.utils.Screen
import com.example.wavewash.utils.headerOrderButtons

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JanitorDetailsScreen(
    navController: NavController,
    viewModel: JanitorDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val openDialogCustom =  remember { mutableStateOf(false) }

    val screenResultState = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.get<String>(REFRESH_ORDER)

    screenResultState?.let { value ->
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.remove<String>(REFRESH_ORDER)

        if (value == REFRESH_ORDER) {
            viewModel.onTriggerEvent(JanitorDetailEvent.ReloadOrders)
        }

    }

    if(openDialogCustom.value){
        CalendarDialog(
            openDialogCustom = openDialogCustom,
            dateRangeSelected = { dateFrom,dateTo->
                viewModel.onTriggerEvent(JanitorDetailEvent.ChangeDates(dateFrom,dateTo))
            }
        )
    }

    Column{
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            BackButton(
                onBackClicked = {
                    navController.popBackStack()
                }
            )
            Calendar(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(Shapes.small)
                    .background(Color.White),
                beginDate = state.calendarDateFrom,
                endDate = state.calendarDateTo,
                onCalendarPopup = {
                    openDialogCustom.value = true
                },
                onNextClick = {
                    viewModel.onTriggerEvent(JanitorDetailEvent.OnNextDateClick)
                },
                onPreviousClick = {
                    viewModel.onTriggerEvent(JanitorDetailEvent.OnPreviousDateClick)
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            state.washer?.let {
                CardsJanitorInfo(
                    modifier = Modifier
                        .weight(0.66f)
                        .fillMaxHeight(1f),
                    washer = it,
                )
            }
            CardsJanitorAnalytics(
                modifier = Modifier
                    .weight(0.34f)
                    .fillMaxHeight(1f),
                earnedMoney = state.earnedMoney,
                earnedStake = state.earnedStake
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
                .clip(Shapes.large)
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
//            ScreenHeaders(
//                headers = headerOrderButtons,
//                selectedOption = "Новый заказ",
//                onClick = { index ->
//                    when (index) {
//                        0 -> navController.navigate(Screen.NewOrderScreenRoute.route)
//                    }
//                }
//            )
            OrdersTab(
                onClick = { index->
                    if(index==0){
                        viewModel.onTriggerEvent(JanitorDetailEvent.ActiveOrders)
                    }
                    else{
                        viewModel.onTriggerEvent(JanitorDetailEvent.FinishedOrders)
                    }
                }
            )
            HeadersOfList()
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.orders.size) { index ->
                    OrderItem(
                        order = state.orders[index],
                        onClick = {
                            navController.navigate(Screen.OrderDetailsScreenRoute.route + "/${state.orders[index].id}")
                        }
                    )
                    Divider(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}
