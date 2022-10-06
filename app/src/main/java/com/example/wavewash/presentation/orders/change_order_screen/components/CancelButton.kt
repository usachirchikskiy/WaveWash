package com.example.wavewash.presentation.orders.change_order_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.utils.ComposeString

@Composable
fun CancelButton(onClick:()->Unit) {
    Row(
        modifier = Modifier
            .clip(Shapes.medium)
            .clickable {
                //TODO back button
            }
            .border(
                width = 1.dp,
                color = Color.Red,
                shape = Shapes.medium
            )
            .padding(horizontal = 21.dp, vertical = 10.dp)
            .clickable { onClick.invoke() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.cancel),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .height(30.dp)
                .padding(
                    start = 8.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = ComposeString.resource(R.string.cancel_btn).value(),
                style = MaterialTheme.typography.body1,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = Color.Red
            )
        }
    }
}