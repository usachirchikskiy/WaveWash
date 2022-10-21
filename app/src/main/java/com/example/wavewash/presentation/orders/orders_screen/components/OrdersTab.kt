package com.example.wavewash.presentation.orders.orders_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wavewash.ui.theme.*
import com.example.wavewash.utils.ComposeString
import com.example.wavewash.utils.ordersTab

@Composable
fun OrdersTab(
    onClick:(Int)->Unit
) {
    val text =  ComposeString.resource(ordersTab[0].title).value()
    val selectedOption = remember { mutableStateOf(text) }

    Row(
        modifier = Modifier
            .background(Color.White)
            .padding(bottom = 24.dp)
    ) {
        ordersTab.forEachIndexed { index, ordersScreenTab ->
            val btnText = ComposeString.resource(ordersScreenTab.title).value()
            val selected =
                selectedOption.value == btnText

            Column(
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .clickable {
                        selectedOption.value = btnText
                        onClick.invoke(index)
                    }
            ) {
                Text(
                    modifier = Modifier.padding(start = 36.dp, end = 36.dp),
                    text = ComposeString.resource(ordersScreenTab.title).value(),
                    style = if (selected) {
                        ordersTabActive
                    } else {
                        ordersTabInActive
                    },
                    color = if (selected) {
                        ActiveButtonBackground
                    } else {
                        HeaderButtonColor
                    },
                )

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = if (selected) 10.dp
                            else {
                                11.dp
                            }
                        )
                    ,
                    color = if (selected) ActiveButtonBackground
                    else {
                        HeaderButtonStroke
                    },
                    thickness = if (selected) 2.dp
                    else {
                        1.dp
                    }
                )
            }
        }

        Divider(
            modifier = Modifier.align(Alignment.Bottom).padding(bottom = 0.5.dp),
            color = HeaderButtonStroke
        )
    }
}

//                modifier = Modifier.background(
//                    if (selected) ActiveButtonBackground
//                    else {
//                        HeaderButtonColor
//                    }
//                )
//                Text(
//                    text = ComposeString.resource(ordersScreenTab.title).value(),
//                    modifier = Modifier.padding(start = 36.dp, end = 36.dp),
//                    style = if (selected) {
//                        MaterialTheme.typography.body1
//                    } else {
//                        MaterialTheme.typography.h2
//                    },
//                    color = if (selected) {
//                        ActiveButtonBackground
//                    } else {
//                        HeaderButtonColor
//                    },
//                    fontSize = 12.sp
//                )


