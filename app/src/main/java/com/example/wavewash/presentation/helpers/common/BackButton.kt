package com.example.wavewash.presentation.helpers.common

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.ui.theme.QuantityOfServices
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.utils.ComposeString

@Composable
fun BackButton(
    onBackClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(Shapes.medium)
            .background(QuantityOfServices)
            .clickable {
                onBackClicked.invoke()
            }
            .padding(horizontal = 36.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.arrow_back),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = ComposeString.resource(R.string.back).value(),
            style = MaterialTheme.typography.body1,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}
