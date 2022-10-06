package com.example.wavewash.presentation.orders.order_details_screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.ui.theme.HeaderButtonStroke
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.ui.theme.TextColor
import com.example.wavewash.utils.ComposeString

@Composable
fun ChangeButton(onClick:()->Unit) {
    Box(
        modifier = Modifier
            .clip(Shapes.medium)
            .clickable {
                onClick.invoke()
            }
            .border(
                width = 1.dp,
                color = HeaderButtonStroke,
                shape = Shapes.medium
            )
            .padding(horizontal = 42.dp, vertical = 10.dp)
        ) {
        Box(
            modifier = Modifier
                .height(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = ComposeString.resource(R.string.change).value(),
                style = MaterialTheme.typography.body1,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = TextColor
            )
        }
    }
}