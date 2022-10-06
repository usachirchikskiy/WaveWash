package com.example.wavewash.presentation.helpers.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wavewash.R
import com.example.wavewash.ui.theme.appNameStyle
import com.example.wavewash.ui.theme.ordersTabActive
import com.example.wavewash.utils.ComposeString

@Composable
fun LogoNameCompany(
    onClick:()->Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(R.drawable.logo),
            contentDescription = "logo",
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.width(16.dp))
        Text(
            style = appNameStyle,
            text = ComposeString.resource(R.string.app_name).value()
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.clickable {
                onClick.invoke()
            }
        ) {
            Text(
                style = ordersTabActive,
                text = ComposeString.resource(R.string.car_wash_name).value()
            )
            Image(
                painterResource(R.drawable.logo),
                contentDescription = "logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}