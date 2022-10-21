package com.example.wavewash.presentation.helpers.popups.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.ui.theme.SupportAnswerBorder
import com.example.wavewash.ui.theme.HeaderButtonStroke
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.ui.theme.nunitoSans
import com.example.wavewash.utils.ComposeString
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun NewAddPopup(
    isJanitor: Boolean = false,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .height(
                if (!isJanitor) 216.dp
                else {
                    340.dp
                }
            )
            .clip(Shapes.medium)
            .border(
                BorderStroke(1.dp, HeaderButtonStroke),
                shape = Shapes.medium
            )
            .clickable {
                onClick.invoke()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isJanitor) {
                ComposeString.resource(R.string.new_n_janitor).value()
            } else {
                ComposeString.resource(R.string.new_n_service).value()
            },
            fontFamily = nunitoSans,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            color = SupportAnswerBorder
        )
        Image(
            modifier = Modifier.padding(top = 16.dp),
            painter = painterResource(R.drawable.add_janitor_popup),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }
}