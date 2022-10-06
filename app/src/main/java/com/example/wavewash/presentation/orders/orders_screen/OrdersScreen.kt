package com.example.wavewash.presentation.orders

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Divider
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wavewash.R
import com.example.wavewash.domain.model.Order
import com.example.wavewash.presentation.helpers.common.Calendar
import com.example.wavewash.presentation.helpers.common.ScreenHeaders
import com.example.wavewash.presentation.orders.orders_screen.components.*
import com.example.wavewash.presentation.helpers.popups.CalendarDialog
import com.example.wavewash.ui.theme.HeaderButtonStroke
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.utils.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrdersScreen(navController: NavController) {
    val openDialogCustom =  remember { mutableStateOf(false) }
    val list = mutableListOf<Order>()
    for (i in 0..30){
        list.add(
            Order(
                "https://www.dmarge.com/wp-content/uploads/2021/01/dwayne-the-rock-.jpg",
                "Narbekov Artur asdsa das dsa dsa",
                "https://www.dmarge.com/wp-content/uploads/2021/01/dwayne-the-rock-.jpg",
                "Chevrolet Spark sadsa sad sad adsad sadsa dsad asd sa",
                "10U490PA",
                3,
                "18:30 - 19:30"
            )
        )
    }

//    val listState = rememberLazyListState()

    if(openDialogCustom.value){
        CalendarDialog(openDialogCustom = openDialogCustom)
    }
    Column{
        Calendar(
            Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
                .clip(Shapes.small)
                .background(Color.White),
            onCalendarPopup = {
                openDialogCustom.value = true
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
                        headerOrderButtons,
                        ComposeString.resource(R.string.add_order).value(),
                        onClick = { index->
                            when (index){
                                0 -> navController.navigate(Screen.NewOrderScreenRoute.route)
                            }
                        }
                    ) // TODO change
                    OrdersTab()
                }
            }
            stickyHeader {
                HeadersOfList()
            }

            items(list.size) { index ->

                if (index >= list.size - 1 ) {
                    println("Reached End")
                }
                OrderItem(
                    list[index],
                    onClick = {
                        navController.navigate(Screen.OrderDetailsScreenRoute.route)
                    }
                )
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = HeaderButtonStroke
                )
            }
        }

    }

//    listState.OnBottomReached {
//        println("Here")
//    }

}


@Composable
fun LazyListState.OnBottomReached(
    loadMore : () -> Unit
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf true

            lastVisibleItem.index == layoutInfo.totalItemsCount - 1
        }
    }

    // Convert the state into a cold flow and collect
    LaunchedEffect(shouldLoadMore){
        snapshotFlow { shouldLoadMore.value }
            .collect {
                // if should load more, then invoke loadMore
                if (it) loadMore()
            }
    }
}
