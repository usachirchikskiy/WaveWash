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
    washer: Washer,
    scaleSize: Double,
    scaleRegulator: Double
) {
    Row(
        modifier = modifier
            .padding(top = 28.dp)
            .clip(Shapes.large)
            .background(Color.White)
            .padding(20.dp)
    ) {
        Box() {
            CoilImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .size(186.dp),
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
                    .padding(
                        start = 16.dp,
                        top = 16.dp
                    )
            ) {
                Text(
                    modifier = Modifier
                        .clip(Shapes.medium)
                        .background(BackOfOrdersList)
                        .padding(
                            horizontal = 9.dp, vertical = 5.dp
                        ),
                    text = if (washer.status) ComposeString.resource(R.string.free).value()
                    else {
                        ComposeString.resource(R.string.busy).value()
                    },
                    fontSize = ((12 - scaleRegulator) * scaleSize).sp,
                    color = if (washer.status) StatusFree
                    else {
                        StatusBusy
                    }
                )
            }
        }


        Column(
            modifier = Modifier
                .padding(start = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = washer.fullName + " Artur",
                style = MaterialTheme.typography.h1,
                fontSize = ((28 - scaleRegulator) * scaleSize).sp,
                color = GreyTextColor,
                maxLines = 2
            )
            Text(
                modifier = Modifier.padding(top = 13.dp),
                text = washer.phoneNumber,
                style = MaterialTheme.typography.h1,
                fontSize = ((20 - scaleRegulator) * scaleSize).sp
            )
            Row(modifier = Modifier.padding(top = 11.dp)) {
                Text(
                    text = ComposeString.resource(R.string.stake_from_service).value(),
                    fontFamily = FontFamily(Font(R.font.nunitosans_regular)),
                    fontSize = ((16 - scaleRegulator) * scaleSize).sp
                )
                Text(
                    text = " " + washer.stake.toString() +"%",
                    fontFamily = FontFamily(Font(R.font.nunitosans_bold)),
                    fontSize = ((16 - scaleRegulator) * scaleSize).sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Row {
                Spacer(modifier = Modifier.weight(1f))
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
                        ),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 34.dp, vertical = 10.dp),
                        text = ComposeString.resource(R.string.change).value(),
                        style = TextStyle(
                            color = HeaderButtonColor,
                            fontFamily = nunitoSans,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = ((18 - scaleRegulator) * scaleSize).sp
                        )
                    )
                }
            }
        }
    }
}
