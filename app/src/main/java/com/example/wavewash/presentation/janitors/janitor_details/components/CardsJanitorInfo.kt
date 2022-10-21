package com.example.wavewash.presentation.janitors.janitor_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.domain.model.Washer
import com.example.wavewash.ui.theme.*
import com.example.wavewash.utils.ComposeString
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun CardsJanitorInfo(
    modifier: Modifier,
    washer: Washer
) {
    Row(
        modifier = modifier
            .padding(top = 32.dp)
            .clip(Shapes.large)
            .background(Color.White)
            .padding(24.dp)
    ) {
        Box() {
            CoilImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .size(180.dp),
                imageModel = washer.imageUrl,
                contentScale = ContentScale.Crop,            // crop the image if it's not a square

                shimmerParams = ShimmerParams(
                    baseColor = Color.White,
                    highlightColor = Color.LightGray,
                    durationMillis = 350,
                    dropOff = 0.65f,
                    tilt = 20f
                ),
                // shows an error text message when request failed.
                failure = {
                    Text(
                        text = "image request failed.",
                    )
                }
            )
            Box(
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .clip(Shapes.medium)
                    .background(BackOfOrdersList)
                    .padding(horizontal = 9.dp, vertical = 5.dp)
            ) {
                Text(
                    text = if (washer.status) ComposeString.resource(R.string.free).value()
                    else {
                        ComposeString.resource(R.string.busy).value()
                    },
                    fontSize = 12.sp,
                    color = if (washer.status) StatusFree
                    else {
                        StatusBusy
                    },
                    fontWeight = FontWeight.Bold
                )
            }
        }


        Column(
            modifier = Modifier
                .padding(start = 24.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = washer.fullName,
                style = MaterialTheme.typography.h1,
                fontSize = 24.sp,
                color = GreyTextColor,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = washer.phoneNumber,
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextColor
            )

            Row {
                Text(
                    text = ComposeString.resource(R.string.stake_from_service).value(),
                    fontFamily = nunitoSans,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
                Text(
                    text = " " + washer.stake.toString() + "%",
                    fontFamily = nunitoSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Column(
                modifier = Modifier
                    .clip(
                        shape = Shapes.medium
                    )
                    .clickable {

                    }
                    .border(
                        width = 1.dp,
                        color = HeaderButtonStroke,
                        shape = Shapes.medium
                    )
                    .padding(horizontal = 34.dp, vertical = 10.dp)
            ) {
                Text(
                    text = ComposeString.resource(R.string.change).value(),
                    style = TextStyle(
                        color = HeaderButtonColor,
                        fontFamily = nunitoSans,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}
