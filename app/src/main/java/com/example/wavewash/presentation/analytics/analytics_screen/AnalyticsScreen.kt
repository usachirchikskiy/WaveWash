package com.example.wavewash.presentation.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wavewash.presentation.analytics.analytics_screen.components.OrdersJanitors

import com.example.wavewash.presentation.analytics.analytics_screen.components.OverallPriceField
import com.example.wavewash.presentation.analytics.analytics_screen.components.StakeJanitorField
import com.example.wavewash.presentation.helpers.common.Calendar
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.utils.Screen


@Composable
fun AnalyticsScreen(
    navController: NavController
) {
    //TODO prices added
    Column(
        modifier = Modifier
            .padding(bottom = 24.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
    ) {
        Calendar(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
                .clip(Shapes.small)
                .background(Color.White),
            onCalendarPopup = {

            }
        )
        OrdersJanitors()
        Divider(
            thickness = 1.dp,
            color = Color(0xFFEFF1F8)
        )
        OverallPriceField(
            //navController
            onClick = {
                navController.navigate(Screen.OverallPriceScreenRoute.route)
            }
        )
        StakeJanitorField(
            onClick = {
                navController.navigate(Screen.JanitorStakeScreenRoute.route)
            }
        )
    }
}