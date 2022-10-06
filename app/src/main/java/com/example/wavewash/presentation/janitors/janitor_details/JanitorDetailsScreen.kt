package com.example.wavewash.presentation.janitors.janitor_details

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wavewash.domain.model.Order
import com.example.wavewash.domain.model.Washer
import com.example.wavewash.presentation.helpers.common.BackButton
import com.example.wavewash.presentation.helpers.common.Calendar
import com.example.wavewash.presentation.helpers.common.ScreenHeaders
import com.example.wavewash.presentation.janitors.janitor_details.components.CardsJanitorAnalytics
import com.example.wavewash.presentation.janitors.janitor_details.components.CardsJanitorInfo
import com.example.wavewash.presentation.orders.orders_screen.components.HeadersOfList
import com.example.wavewash.presentation.orders.orders_screen.components.OrderItem
import com.example.wavewash.presentation.orders.orders_screen.components.OrdersTab
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.utils.Screen
import com.example.wavewash.utils.headerOrderButtons

@Composable
fun JanitorDetailsScreen(
    navController: NavController,
    configuration: Configuration
) {
    var scaleSize = 1.0
    var scaleRegulator = 4.0
    val washer = Washer(
        1,
        "Нарбеков",
        "+998917788098",
        true,
        "https://www.dmarge.com/wp-content/uploads/2021/01/dwayne-the-rock-.jpg",
        15
    )
    val list = listOf<Order>(
        Order(
            "https://www.dmarge.com/wp-content/uploads/2021/01/dwayne-the-rock-.jpg",
            "Narbekov Artur",
            "https://www.dmarge.com/wp-content/uploads/2021/01/dwayne-the-rock-.jpg",
            "Chevrolet Spark",
            "10E123TA",
            3,
            "18:30 - 19:30"
        ),
        Order(
            "https://www.dmarge.com/wp-content/uploads/2021/01/dwayne-the-rock-.jpg",
            "Narbekov Artur",
            "https://www.dmarge.com/wp-content/uploads/2021/01/dwayne-the-rock-.jpg",
            "Chevrolet Spark",
            "10E123TA",
            3,
            "18:30 - 19:30"
        ),
        Order(
            "https://www.dmarge.com/wp-content/uploads/2021/01/dwayne-the-rock-.jpg",
            "Narbekov Artur",
            "https://www.dmarge.com/wp-content/uploads/2021/01/dwayne-the-rock-.jpg",
            "Chevrolet Spark",
            "10E123TA",
            3,
            "18:30 - 19:30"
        )
    )

    when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            scaleSize = 0.66
            scaleRegulator = 0.0
        }
    }

    Column(
        modifier = Modifier.padding(vertical = 28.dp)
    ) {
        DetailAppBar(navController)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CardsJanitorInfo(
                modifier = Modifier
                    .weight(0.66f)
                    .fillMaxHeight(1f),
                washer = washer,
                scaleSize = scaleSize,
                scaleRegulator = scaleRegulator
            )
            CardsJanitorAnalytics(
                modifier = Modifier
                    .weight(0.34f)
                    .fillMaxHeight(1f),
                washer = washer,
                scaleSize = scaleSize,
                scaleRegulator = scaleRegulator
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
            ScreenHeaders(
                headers = headerOrderButtons,
                selectedOption = "Новый заказ",
                onClick = { index ->
                    when (index) {
                        0 -> navController.navigate(Screen.NewOrderScreenRoute.route)
                    }
                }
            )
            OrdersTab()
            HeadersOfList()
            LazyColumn {
                items(list.size) { index ->
                    OrderItem(
                        list[index],
                        onClick = {
                            navController.navigate(Screen.OrderDetailsScreenRoute.route)
                        }
                    )
                    Divider(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Composable
fun DetailAppBar(navController: NavController) {
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
            Modifier
                .fillMaxWidth()
                .clip(Shapes.small)
                .background(Color.White),
            onCalendarPopup = {

            }
        )
    }
}