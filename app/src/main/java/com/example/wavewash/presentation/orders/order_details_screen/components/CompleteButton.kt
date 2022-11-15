package com.example.wavewash.presentation.orders.order_details_screen.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.ui.theme.ActiveButtonBackground
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.utils.ComposeString

@Composable
fun CompleteButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(Shapes.medium)
            .background(ActiveButtonBackground)
            .clickable {
                onClick.invoke()
            }
            .padding(horizontal = 24.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = ComposeString.resource(R.string.complete).value(),
            style = MaterialTheme.typography.body1,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}