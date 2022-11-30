package com.example.wavewash.presentation.helpers.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wavewash.R
import com.example.wavewash.ui.theme.ActiveButtonBackground
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.ui.theme.appNameStyle
import com.example.wavewash.ui.theme.ordersTabActive
import com.example.wavewash.utils.ComposeString

@Composable
fun LogoNameCompany(
    onCompanyClick: () -> Unit,
    onLogoClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
            onLogoClick.invoke()
        }) {
            Image(
                painterResource(R.drawable.logo),
                contentDescription = "logo",
                contentScale = ContentScale.Crop
            )
        }
        Text(
            style = appNameStyle,
            text = ComposeString.resource(R.string.app_name).value()
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .clickable {
                    onCompanyClick.invoke()
                }
                .padding(12.dp, 10.dp),
            verticalAlignment = Alignment.CenterVertically
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