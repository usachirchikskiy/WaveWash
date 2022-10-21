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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wavewash.presentation.helpers.common.BackButton
import com.example.wavewash.presentation.helpers.common.Logo
import com.example.wavewash.presentation.orders.order_details_screen.components.*
import com.example.wavewash.ui.theme.HeaderButtonStroke
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.utils.REFRESH_ORDER
import com.example.wavewash.utils.Screen

@Composable
fun OrderDetailsScreen(
    navController: NavController,
    viewModel: OrderDetailViewModel = hiltViewModel()
) {

    val state = viewModel.state

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Logo()
        Column(
            modifier = Modifier
                .padding(top = 25.dp, bottom = 24.dp)
                .fillMaxWidth()
                .clip(Shapes.large)
                .background(Color.White)
                .padding(16.dp, 24.dp)
        ) {
            TextMinutes(
                duration = state.duration
            )
            Divider(
                modifier = Modifier.padding(top = 24.dp),
                color = HeaderButtonStroke,
                thickness = 1.dp
            )

            state.washers.forEach {
                JanitorNameNumber(
                    washerName = it.name,
                    washerNumber = it.telephoneNumber.toString()
                )
            }

            Divider(
                modifier = Modifier.padding(top = 24.dp),
                color = HeaderButtonStroke,
                thickness = 1.dp
            )
            CarNumberColor(
                carName = state.carModel,
                carNumber = state.carNumber
            )
            Divider(
                modifier = Modifier.padding(top = 24.dp),
                color = HeaderButtonStroke,
                thickness = 1.dp
            )
            state.services.forEach {
                ServicePriceDuration(
                    serviceName = it.name,
                    price = it.price.toString(),
                    duration = it.duration.toString()
                )
            }
            Divider(
                modifier = Modifier.padding(top = 24.dp),
                color = HeaderButtonStroke,
                thickness = 1.dp
            )
            ClientNumberName(
                clientName = state.clientName,
                clientNumber = state.clientNumber.toString()
            )
            Divider(
                modifier = Modifier.padding(top = 24.dp),
                color = HeaderButtonStroke,
                thickness = 1.dp
            )
            Price(
                orderPrice = state.price.toString(),
                janitorStakePrice = state.priceOfJanitorsStake.toString()
            )
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
                if (state.isActive) {
                    ChangeButton(
                        onClick = {
                            navController.navigate(Screen.ChangeOrderScreenRoute.route + "/${state.id}") {
                                popUpTo(Screen.MainScreenRoute.route)
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            if (state.isActive) {
                CompleteButton(
                    onClick = {
                        viewModel.onTriggerEvent(OrderDetailEvent.CompleteOrder)
                    }
                )
            }

            if (state.completed) {
                viewModel.changeCompleted()
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(REFRESH_ORDER, REFRESH_ORDER)
                navController.popBackStack()
            }
        }
    }
}