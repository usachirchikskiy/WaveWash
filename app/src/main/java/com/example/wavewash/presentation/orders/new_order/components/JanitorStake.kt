package com.example.wavewash.presentation.orders.new_order.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.domain.model.Washer
import com.example.wavewash.ui.theme.*
import com.example.wavewash.utils.ComposeString

@Composable
fun JanitorStake(
    washerError: Int?,
    washerOrderOrNot: Boolean,
    onClick: () -> Unit,
    washers: List<Washer>,
    priceOfJanitorsStake: String,
    onDeleteWasherClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .padding(top = 36.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = ComposeString.resource(R.string.janitor).value(),
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
                        color = if (washerError != null) {
                            MaterialTheme.colors.error
                        } else {
                            Color(0XFFD3DDEC)
                        },
                        shape = Shapes.small
                    )
                    .background(
                        color = if (!washerOrderOrNot) {
                            Color.White
                        } else {
                            Color.LightGray
                        },
                        shape = Shapes.small
                    )
                    .padding(horizontal = 14.dp, vertical = 9.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (washers.size == 1) {
                    Text(
                        modifier = if (!washerOrderOrNot) {
                            Modifier
                                .weight(1f)
                                .clickable {
                                    onDeleteWasherClick.invoke()
                                }
                        } else {
                            Modifier
                                .weight(1f)
                        },
                        text = washers[0].name,
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
                            onDeleteWasherClick.invoke()
                        }
                        .padding(10.dp, 5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = washers.size.toString() + ComposeString.resource(R.string.quantity)
                                .value(),
                            color = TextColor,
                        )
                    }

                }

                if (!washerOrderOrNot) {
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
            if (washerError != null) {
                Text(
                    text = stringResource(id = washerError),
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }

        Spacer(Modifier.weight(0.1f))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = ComposeString.resource(R.string.janitors_stake).value(),
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
            ) {
                Text(
                    text = priceOfJanitorsStake + " сум",
                    fontFamily = nunitoSans,
                    fontSize = 14.sp,
                    color = TextColor,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }

}