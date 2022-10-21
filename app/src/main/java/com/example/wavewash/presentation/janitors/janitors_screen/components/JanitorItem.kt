package com.example.wavewash.presentation.janitors.janitors_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.data.remote.dto.washer.WasherAnswerDto
import com.example.wavewash.ui.theme.*
import com.example.wavewash.utils.ComposeString
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun JanitorItem(
    washer: WasherAnswerDto,
    onJanitorClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable {
                onJanitorClicked.invoke()
            },
        shape = Shapes.medium,
        elevation = 0.dp,
        border = BorderStroke(1.dp, HeaderButtonStroke),
    ) {
        Column {
            //Image and Status
            Box() {
                CoilImage(
                    modifier = Modifier
                        .height(216.dp),
                    imageModel = "https://media-exp2.licdn.com/dms/image/C4D03AQFB8ojf_-tmiw/profile-displayphoto-shrink_200_200/0/1601490730164?e=2147483647&v=beta&t=lJVY_VeGE6Vp_VPV4yCrgxQgv-1qsDac6Ut9A2Ey4xw",
                    contentScale = ContentScale.Crop,

                    shimmerParams = ShimmerParams(
                        baseColor = Color.White,
                        highlightColor = Color.LightGray,
                        durationMillis = 350,
                        dropOff = 0.65f,
                        tilt = 20f
                    ),
                    // shows an error text message when request failed.
                    failure = {
                        Text(text = "image request failed.")
                    }
                )
                Box(
                    modifier = Modifier
                        .padding(
                            16.dp
                        )
                ) {
                    Text(
                        modifier = Modifier
                            .clip(Shapes.medium)
                            .background(BackOfOrdersList)
                            .padding(
                                horizontal = 9.dp, vertical = 5.dp
                            ),
                        text = if (washer.active) ComposeString.resource(R.string.free).value()
                        else {
                            ComposeString.resource(R.string.busy).value()
                        },
                        fontSize = 12.sp,
                        color = if (washer.active) StatusFree
                        else {
                            StatusBusy
                        }

                    )
                }
            }

            //Name and phone
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Text(
                    text = washer.name,
                    maxLines = 1,
                    style = MaterialTheme.typography.body1,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    maxLines = 1,
                    modifier = Modifier.padding(top = 8.dp),
                    text = "+998 ${washer.telephoneNumber}",
                    fontFamily = nunitoSans,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = HeaderButtonColor
                )

            }
        }
    }
}