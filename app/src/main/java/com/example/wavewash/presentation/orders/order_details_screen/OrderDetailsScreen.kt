package com.example.wavewash.presentation.orders.order_details_screen

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
import com.example.wavewash.presentation.helpers.common.BackButton
import com.example.wavewash.presentation.helpers.common.Logo
import com.example.wavewash.presentation.orders.order_details_screen.components.*
import com.example.wavewash.ui.theme.HeaderButtonStroke
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.utils.Screen

@Composable
fun OrderDetailsScreen(navController: NavController) {

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Logo()
        Column(
            modifier = Modifier
                .padding(top = 31.dp, bottom = 24.dp)
                .fillMaxWidth()
                .clip(Shapes.large)
                .background(Color.White)
                .padding(16.dp, 24.dp)
        ) {
            TextMinutes()
            Divider(
                modifier = Modifier.padding(top = 24.dp),
                color = HeaderButtonStroke,
                thickness = 1.dp
            )
            JanitorNameNumber()
            Divider(
                modifier = Modifier.padding(top = 24.dp),
                color = HeaderButtonStroke,
                thickness = 1.dp
            )
            CarNumberColor()
            Divider(
                modifier = Modifier.padding(top = 24.dp),
                color = HeaderButtonStroke,
                thickness = 1.dp
            )
            ServicePriceDuration()
            Divider(
                modifier = Modifier.padding(top = 24.dp),
                color = HeaderButtonStroke,
                thickness = 1.dp
            )
            ClientNumberName()
            Divider(
                modifier = Modifier.padding(top = 24.dp),
                color = HeaderButtonStroke,
                thickness = 1.dp
            )
            Price()
        }
        Row {
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                BackButton(
                    onBackClicked = {
                        navController.popBackStack()
                    }
                )

                ChangeButton(
                    onClick = {
                        navController.navigate(Screen.ChangeOrderScreenRoute.route)
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            CompleteButton(
                onClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}