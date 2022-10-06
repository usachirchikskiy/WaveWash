package com.example.wavewash.presentation.account.account_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.ui.theme.TextColor
import com.example.wavewash.ui.theme.nunitoSans
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage


@Composable
fun LogoNameLocation() {
    val logo = "https://www.dmarge.com/wp-content/uploads/2021/01/dwayne-the-rock-.jpg"
    val name = "Avtoritet"
    Row(
        modifier = Modifier
            .padding(top = 24.dp)
            .clip(Shapes.large)
            .background(Color.White)
            .padding(24.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        CoilImage(
            imageModel = logo,
            contentScale = ContentScale.Crop,// crop the image if it's not a square
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(16.dp)),
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
            })

        Column(modifier = Modifier.padding(start = 32.dp)) {
            Text(
                text = name,
                color = TextColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = nunitoSans,
            )

            Row(
                modifier = Modifier.padding(top = 9.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.location),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = name,
                    color = TextColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = nunitoSans,
                    )
            }
        }
    }
}