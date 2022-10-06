package com.example.wavewash.presentation.orders.new_order

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wavewash.presentation.helpers.common.AddButton
import com.example.wavewash.presentation.helpers.common.BackButton
import com.example.wavewash.presentation.helpers.common.Logo
import com.example.wavewash.presentation.orders.new_order.components.*
import com.example.wavewash.presentation.helpers.popups.ChooseJanitorDialog
import com.example.wavewash.presentation.helpers.popups.ChooseServiceDialog
import com.example.wavewash.ui.theme.HeaderButtonStroke
import com.example.wavewash.ui.theme.Shapes

@Composable
fun NewOrderScreen(navController: NavController) {
    val openDialogCustom = remember { mutableStateOf(false) }
    val openDialogCustomJanitor = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Logo()
        Column(
            modifier = Modifier
                .padding(top = 25.dp, bottom = 24.dp)
                .fillMaxWidth()
                .clip(Shapes.large)
                .background(Color.White)
                .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 72.dp)
        ) {
            TextMinutes()

            ServicePrice(
                onClick = {
                    openDialogCustom.value = true
                }
            )
            if (openDialogCustom.value) {
                ChooseServiceDialog(openDialogCustom = openDialogCustom)
            }

            Divider(
                modifier = Modifier.padding(top = 36.dp),
                color = HeaderButtonStroke,
                thickness = 1.dp
            )
            JanitorStake(
                onClick = {
                    openDialogCustomJanitor.value = true
                }
            )
            if (openDialogCustomJanitor.value) {
                ChooseJanitorDialog(openDialogCustom = openDialogCustomJanitor)
            }

            Divider(
                modifier = Modifier.padding(top = 36.dp),
                color = HeaderButtonStroke,
                thickness = 1.dp
            )
            CarModelNumber()

            Divider(
                modifier = Modifier.padding(top = 36.dp),
                color = HeaderButtonStroke,
                thickness = 1.dp
            )
            ClientNumberName()
        }

        //buttons
        Row {
            BackButton(
                onBackClicked = {
                    navController.popBackStack()
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            AddButton(
                onClick = {

                }
            )
        }
    }
}