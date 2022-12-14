package com.example.wavewash.presentation.analytics.janitor_stake

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wavewash.R
import com.example.wavewash.domain.model.Order
import com.example.wavewash.presentation.helpers.common.BackButton
import com.example.wavewash.presentation.helpers.common.LogoCalendar
import com.example.wavewash.presentation.helpers.common.ScreenHeaders
import com.example.wavewash.presentation.orders.orders_screen.components.OrderItem
import com.example.wavewash.presentation.orders.orders_screen.components.StakeHeadersOfList
import com.example.wavewash.ui.theme.HeaderButtonStroke
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.utils.ComposeString
import com.example.wavewash.utils.headerOverallButtons

@Composable
fun JanitorStakeScreen(
    navController: NavController
) {

    Column {
        LogoCalendar()
        Column(
            modifier = Modifier
                .padding(top = 25.dp, bottom = 24.dp)
                .fillMaxWidth()
                .clip(Shapes.large)
                .background(Color.White)
                .padding(16.dp, 24.dp)
        ) {
            Text(
                text = ComposeString.resource(R.string.janitors_stake).value(),
                style = MaterialTheme.typography.body1,
                fontSize = 24.sp,
            )

            Text(
                modifier = Modifier.padding(top = 24.dp, bottom = 24.dp),
                text = ComposeString.resource(R.string.janitors_stake).value(),
                style = MaterialTheme.typography.body1,
                fontSize = 35.sp,
            )

//            ScreenHeaders(
//                headers = headerOverallButtons,
//                selectedOption = "",
//                onClick = {
//                    //Todo export
//                }
//            )
            StakeHeadersOfList()
            LazyColumn {
//                items(list.size) { index ->
//                    OrderItem(
//                        list[index],
//                        onClick = {
//
//                        }
//                    )
//                    Divider(
//                        modifier = Modifier.fillMaxWidth(),
//                        color = HeaderButtonStroke
//                    )
            }
        }
    }//Column

    BackButton(
        onBackClicked = {
            navController.popBackStack()
        }
    )

}

