package com.example.wavewash.presentation.helpers.popups.janitor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.wavewash.R
import com.example.wavewash.domain.model.Washer
import com.example.wavewash.presentation.helpers.common.SearchBar
import com.example.wavewash.presentation.helpers.popups.CustomJanitorDialogUI
import com.example.wavewash.presentation.helpers.popups.components.PopupExit
import com.example.wavewash.presentation.helpers.popups.components.PopupExitMiniRed
import com.example.wavewash.presentation.janitors.janitors_screen.JanitorEvents
import com.example.wavewash.ui.theme.*
import com.example.wavewash.utils.ComposeString
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage


@Composable
fun DeleteJanitorDialog(
    onClose: () -> Unit,
    onDeleteClicked: (Washer) -> Unit,
    janitors:List<Washer>
) {
    Dialog(
        onDismissRequest = {
            onClose.invoke()
        }
    ) {
        DeleteJanitorDialogUI(
            onClose = { onClose.invoke() },
            janitors = janitors,
            onDeleteClicked = {onDeleteClicked.invoke(it)})
    }
}

@Composable
fun DeleteJanitorDialogUI(
    onClose: () -> Unit,
    janitors: List<Washer>,
    onDeleteClicked: (Washer) -> Unit
) {
    Column(
        modifier = Modifier
            .clip(Shapes.large)
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = ComposeString.resource(R.string.janitors).value(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = TextColor
            )
            Spacer(modifier = Modifier.weight(1f))
            PopupExit(onClose = {
                onClose.invoke()
            })
        }

        Divider(
            modifier = Modifier.padding(vertical = 24.dp),
            color = HeaderButtonStroke
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(Shapes.medium)
                .background(BackOfOrdersList)
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = ComposeString.resource(R.string.name).value(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = TextColor
            )
        }

        LazyColumn() {
            items(janitors.size) { index ->

                DeleteJanitorItem(
                    washer = janitors[index],
                    deleteJanitorClicked = {
                        onDeleteClicked.invoke(it)
                    }
                )

                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = HeaderButtonStroke
                )
            }
        }

    }
}

@Composable
fun DeleteJanitorItem(
    washer: Washer,
    deleteJanitorClicked: (Washer) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CoilImage(
            imageModel = "",//TODO washer URL
            contentScale = ContentScale.Crop,// crop the image if it's not a square
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape),

            shimmerParams = ShimmerParams(
                baseColor = Color.White,
                highlightColor = Color.LightGray,
                durationMillis = 350,
                dropOff = 0.65f,
                tilt = 20f
            ),
            // shows an error text message when request failed.
            failure = {
                Text(text = "image request failed.")
            })

        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = washer.name,
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TextColor,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(Modifier.weight(1f))

        PopupExitMiniRed {
            deleteJanitorClicked.invoke(washer)
        }

    }
}