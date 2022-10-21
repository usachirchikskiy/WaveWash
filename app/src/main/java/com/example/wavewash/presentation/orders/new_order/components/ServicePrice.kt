package com.example.wavewash.presentation.orders.new_order.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.data.remote.dto.service.ServiceAnswerDto
import com.example.wavewash.ui.theme.*
import com.example.wavewash.utils.ComposableString
import com.example.wavewash.utils.ComposeString
import com.example.wavewash.utils.priceOfServices

@Composable
fun ServicePrice(
    onClick: () -> Unit,
    services:List<ServiceAnswerDto>,
    price:String,
    onDeleteServicesClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .padding(top = 36.dp),
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = ComposeString.resource(R.string.service).value(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color(0XFF303972)
            )
            Row(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(1f)
                    .border(
                        width = 1.dp,
                        color = Color(0XFFD3DDEC),
                        shape = Shapes.small
                    )
                    .padding(horizontal = 14.dp, vertical = 9.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (services.size == 1) {
                    Text(
                        modifier = Modifier.weight(1f).clickable {
                            onDeleteServicesClick.invoke()
                        },
                        text = services[0].name,
                        fontFamily = nunitoSans,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = TextColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                } else {
                    Box(modifier = Modifier
                        .clip(Shapes.medium)
                        .background(QuantityOfServices)
                        .clickable {
                            onDeleteServicesClick.invoke()
                        }
                        .padding(10.dp, 5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = services.size.toString() + " " + ComposeString.resource(R.string.quantity)
                                .value(),
                            color = TextColor,
                        )
                    }
                }

//                Spacer(Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .size(30.dp)
                        .background(ActiveButtonBackground)
                        .clickable {
                            onClick.invoke()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.add_order),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        Spacer(Modifier.weight(0.1f))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = ComposeString.resource(R.string.common_price).value(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color(0XFF303972)
            )

            Row(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(1f)
                    .border(
                        width = 1.dp,
                        color = Color(0XFFD3DDEC),
                        shape = Shapes.small
                    )
                    .padding(horizontal = 14.dp, vertical = 9.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = price + " сум",
                    fontFamily = nunitoSans,
                    fontSize = 14.sp,
                    color = TextColor,
                    fontWeight = FontWeight.ExtraBold
                )
            }

//            OutlinedTextField(
//                modifier = Modifier
//                    .padding(top = 5.dp)
//                    .fillMaxWidth(),
//                value = value,
//                onValueChange = { value = it },
//                textStyle = TextStyle(
//                    fontFamily = nunitoSans,
//                    fontSize = 14.sp,
//                    color = TextColor,
//                    fontWeight = FontWeight.ExtraBold
//                ),
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    focusedBorderColor = Color(0XFFD3DDEC), // цвет при получении фокуса
//                    unfocusedBorderColor = Color(0XFFD3DDEC)  // цвет при отсутствии фокуса
//                )
//            )
        }
    }
}