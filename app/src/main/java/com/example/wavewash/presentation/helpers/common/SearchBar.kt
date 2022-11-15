package com.example.wavewash.presentation.helpers.common

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.ui.theme.*
import com.example.wavewash.utils.ComposeString

@Composable
fun SearchBar(
    text:String,
    onSearch: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(Shapes.medium)
            .background(BackOfOrdersList)
            .padding(end = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {


        OutlinedTextField(
            value = text,
            onValueChange = {
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(
                color = TextColor,
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = ActiveButtonBackground,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                // цвет при отсутствии фокуса
            ),
            placeholder = {
                Text(
                    text = "Поиск...",
                    color = HeaderButtonColor,
                    fontFamily = nunitoSans,
                    fontWeight = FontWeight.SemiBold
                )
            },
//            decorationBox = { innerTextField ->
//                if (text.isEmpty()) {
//                    Text(
//                        text = "Поиск...",
//                        color = HeaderButtonColor,
//                        fontFamily = nunitoSans,
//                        fontWeight = FontWeight.SemiBold
//                    )
//                }
//                innerTextField()
//            },
            modifier = Modifier.weight(1f)
        )

        Row(
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                modifier = Modifier.size(18.dp),
                painter = painterResource(R.drawable.search),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }

    }
}
