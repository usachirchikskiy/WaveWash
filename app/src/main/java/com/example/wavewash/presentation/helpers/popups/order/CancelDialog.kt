package com.example.wavewash.presentation.helpers.popups.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.wavewash.R
import com.example.wavewash.presentation.helpers.common.BackButton
import com.example.wavewash.presentation.orders.change_order_screen.components.CancelButton
import com.example.wavewash.ui.theme.ActiveButtonBackground
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.ui.theme.TextColor
import com.example.wavewash.ui.theme.nunitoSans
import com.example.wavewash.utils.ComposeString

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CancelDialog(
    openDialogCustom: MutableState<Boolean>,
    onBackClicked:()->Unit,
    onCancelClicked:(String) -> Unit
) {
    Dialog(
        onDismissRequest = {
            openDialogCustom.value = false
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        CancelDialogUI(
            onBackClicked  = {
                onBackClicked.invoke()
            },
            onCancelClicked =
            {
                onCancelClicked.invoke(it)
            }
        )
    }
}

@Composable
fun CancelDialogUI(
    onBackClicked:()->Unit,
    onCancelClicked:(String) -> Unit
) {
    val message = remember { mutableStateOf("") }
    val isError = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .clip(Shapes.large)
            .background(Color.White)
            .padding(24.dp)
    ) {
        Text(
            text = ComposeString.resource(R.string.cancel_dialog).value(),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = TextColor
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
                .defaultMinSize(minHeight = 196.dp),
            value = message.value,
            onValueChange = { message.value = it },
            textStyle = TextStyle(
                fontFamily = nunitoSans,
                fontSize = 14.sp,
                color = TextColor,
                fontWeight = FontWeight.Normal
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = ActiveButtonBackground,
                focusedBorderColor = Color(0XFFD3DDEC),
                unfocusedBorderColor = Color(0XFFD3DDEC),
            ),
            isError = isError.value,
            maxLines = 7
        )
        if (isError.value) {
            Text(
                text = stringResource(id = R.string.required_field),
                color = MaterialTheme.colors.error,
                modifier = Modifier.align(Alignment.End)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BackButton(
                onBackClicked = {
                    onBackClicked.invoke()
                }
            )

            CancelButton(
                onClick = {
                    if(message.value.isEmpty())isError.value = true
                    else onCancelClicked.invoke(message.value)
                }
            )
        }
    }
}