package com.example.wavewash.presentation.helpers.popups.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.wavewash.R

@Composable
fun PopupExit(
    onClose: () -> Unit
) {
    Image(

        modifier = Modifier
            .clip(CircleShape)
            .clickable {
                onClose.invoke()
            },
        painter = painterResource(R.drawable.close_popup),
        contentDescription = "",
        contentScale = ContentScale.Crop
    )

}