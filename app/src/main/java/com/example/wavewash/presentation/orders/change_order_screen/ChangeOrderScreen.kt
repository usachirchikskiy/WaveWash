package com.example.wavewash.presentation.orders.change_order_screen

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
import com.example.wavewash.R
import com.example.wavewash.presentation.helpers.common.BackButton
import com.example.wavewash.presentation.orders.change_order_screen.components.CancelButton
import com.example.wavewash.presentation.orders.change_order_screen.components.SaveButton
import com.example.wavewash.presentation.helpers.common.Logo
import com.example.wavewash.presentation.orders.new_order.components.*
import com.example.wavewash.ui.theme.HeaderButtonStroke
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.utils.ComposeString

@Composable
fun ChangeOrderScreen(navController:NavController){
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Logo()
        Column(
            modifier = Modifier
                .padding(top = 31.dp, bottom = 24.dp)
                .fillMaxWidth()
                .clip(Shapes.large)
                .background(Color.White)
                .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 72.dp)
        ) {
            TextMinutes(text = ComposeString.resource(R.string.change_of_order).value())
            ServicePrice(
                onClick = {

                }
            )
            Divider(
                modifier  = Modifier.padding(top = 36.dp),
                color = HeaderButtonStroke,
                thickness = 1.dp
            )
            JanitorStake(
                onClick = {

                }
            )
            Divider(
                modifier  = Modifier.padding(top = 36.dp),
                color = HeaderButtonStroke,
                thickness = 1.dp
            )
            CarModelNumber()

            Divider(
                modifier  = Modifier.padding(top = 36.dp),
                color = HeaderButtonStroke,
                thickness = 1.dp
            )
            ClientNumberName()
        }

        //buttons
        Row {
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                BackButton(
                    onBackClicked = {
                        navController.popBackStack()
                    }
                )

                CancelButton(
                    // TODO Change Logic
                    onClick = {
                        navController.popBackStack()
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            SaveButton(
                onClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}