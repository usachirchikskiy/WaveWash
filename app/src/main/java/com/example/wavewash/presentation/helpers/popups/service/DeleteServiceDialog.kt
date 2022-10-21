package com.example.wavewash.presentation.helpers.popups.service

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
import com.example.wavewash.data.remote.dto.service.ServiceAnswerDto
import com.example.wavewash.data.remote.dto.washer.WasherAnswerDto
import com.example.wavewash.presentation.helpers.popups.components.PopupExit
import com.example.wavewash.presentation.helpers.popups.components.PopupExitMiniRed
import com.example.wavewash.presentation.helpers.popups.janitor.DeleteJanitorItem
import com.example.wavewash.ui.theme.*
import com.example.wavewash.utils.ComposeString
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DeleteServiceDialog(
    onClose: () -> Unit,
    onDeleteClicked: (ServiceAnswerDto) -> Unit,
    services: List<ServiceAnswerDto>
) {
    Dialog(
        onDismissRequest = {
            onClose.invoke()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        DeleteServiceDialogUI(
            onClose = { onClose.invoke() },
            services = services,
            onDeleteClicked = {
                onDeleteClicked.invoke(it)
            }
        )
    }
}


@Composable
fun DeleteServiceDialogUI(
    onClose: () -> Unit,
    services: List<ServiceAnswerDto>,
    onDeleteClicked: (ServiceAnswerDto) -> Unit
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
                text = ComposeString.resource(R.string.services).value(),
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
                modifier = Modifier.weight(1/3f),
                text = ComposeString.resource(R.string.service).value(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = TextColor
            )
            Text(
                modifier = Modifier.weight(1/3f),
                text = ComposeString.resource(R.string.price).value(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = TextColor
            )
            Text(
                modifier = Modifier.weight(1/3f),
                text = ComposeString.resource(R.string.time).value(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = TextColor
            )
        }

        LazyColumn() {
            items(services.size) { index ->

                DeleteServiceItem(
                    service = services[index],
                    deleteServiceClicked = {
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
fun DeleteServiceItem(
    service: ServiceAnswerDto,
    deleteServiceClicked: (ServiceAnswerDto) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier  = Modifier.weight(1/3f),
            text = service.name,
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TextColor,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            modifier  = Modifier.weight(1/3f),
            text = service.price.toString(),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TextColor,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            modifier = Modifier.weight(1 / 3f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = service.duration.toString(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = TextColor,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.weight(1f))

            PopupExitMiniRed {
                deleteServiceClicked.invoke(service)
            }
        }
    }
}