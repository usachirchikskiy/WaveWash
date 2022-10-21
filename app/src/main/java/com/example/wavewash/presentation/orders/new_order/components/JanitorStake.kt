package com.example.wavewash.presentation.orders.new_order.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.example.wavewash.data.remote.dto.washer.WasherAnswerDto
import com.example.wavewash.ui.theme.*
import com.example.wavewash.utils.ComposeString
import com.example.wavewash.utils.priceOfServices
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun JanitorStake(
    onClick: () -> Unit,
    washers: List<WasherAnswerDto>,
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
                        color = Color(0XFFD3DDEC),
                        shape = Shapes.small
                    )
                    .padding(horizontal = 14.dp, vertical = 9.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (washers.size == 1) {

//                    CoilImage(
//                        imageModel = "",//TODO worker url
//                        contentScale = ContentScale.Crop,            // crop the image if it's not a square
//                        modifier = Modifier
//                            .size(24.dp)
//                            .clip(CircleShape),
//
//                        shimmerParams = ShimmerParams(
//                            baseColor = Color.White,
//                            highlightColor = Color.LightGray,
//                            durationMillis = 350,
//                            dropOff = 0.65f,
//                            tilt = 20f
//                        ),
//                        // shows an error text message when request failed.
//                        failure = {
//                            Text(text = "image request failed.")
//                        }
//                    )

                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                            .clickable {
                                onDeleteWasherClick.invoke()
                            },
                        text = washers[0].name, //TODO service name
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

                Spacer(Modifier.weight(1f))

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
        } // service block

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