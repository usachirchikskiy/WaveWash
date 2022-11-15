package com.example.wavewash.presentation.helpers.popups.service

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.wavewash.R
import com.example.wavewash.data.remote.dto.service.ServiceDto
import com.example.wavewash.domain.model.Service
import com.example.wavewash.presentation.helpers.common.SearchBar
import com.example.wavewash.presentation.helpers.popups.components.NewAddPopup
import com.example.wavewash.presentation.helpers.popups.components.PopupExit
import com.example.wavewash.ui.theme.*
import com.example.wavewash.utils.ComposeString


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChooseServiceDialog(
    openDialogCustom: MutableState<Boolean>,
    isLoading: Boolean,
    endReached: Boolean,
    services: List<Service>,
    addService: (service: Service) -> Unit,
    onSearchQueryValue: (String) -> Unit,
    loadMore: () -> Unit,
    newServiceClicked: () -> Unit
) {
    Dialog(
        onDismissRequest = {
            openDialogCustom.value = false
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        CustomServiceDialogUI(
            openDialogCustom = openDialogCustom,
            services = services,
            addService = { service ->
                addService.invoke(service)
            },
            isLoading = isLoading,
            endReached = endReached,
            onSearchQueryValue = {
                onSearchQueryValue.invoke(it)
            },
            loadMore = {
                loadMore.invoke()
            },
            newServiceClicked = {
                newServiceClicked.invoke()
            }
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomServiceDialogUI(
    openDialogCustom: MutableState<Boolean>,
    isLoading: Boolean,
    endReached: Boolean,
    services: List<Service>,
    addService: (service: Service) -> Unit,
    onSearchQueryValue: (String) -> Unit,
    loadMore: () -> Unit,
    newServiceClicked: () -> Unit
) {

    Column(
        modifier = Modifier
            .padding(top = 90.dp)
            .clip(Shapes.large)
            .background(Color.White)
            .fillMaxHeight()
            .padding(start = 16.dp, end = 16.dp, top = 24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = ComposeString.resource(R.string.choose_service).value(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = TextColor
            )
            Spacer(modifier = Modifier.weight(1f))
            PopupExit(onClose = {
                openDialogCustom.value = false
            })
        }
        Divider(
            modifier = Modifier.padding(vertical = 24.dp),
            color = HeaderButtonStroke
        )
        SearchBar(
            text = "",
            onSearch = { text ->
                onSearchQueryValue.invoke(text)
            }
        )
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp),
            cells = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item{
                NewAddPopup(
                    onClick = {
                        newServiceClicked.invoke()
                    }
                )
            }
            items(services.size) { index ->

                if (index >= services.size - 1 && !endReached && !isLoading) {
                    loadMore.invoke()
                }

                ServiceItemPopup(
                    service = services[index],
                    addService = { service ->
                        addService.invoke(service)
                    }
                )
            }
        }
    }
}


@Composable
fun ServiceItemPopup(
    service: Service,
    addService: (service: Service) -> Unit
) {

    Card(
        modifier = Modifier
            .height(216.dp)
            .background(Color.White),
        shape = Shapes.medium,
        elevation = 0.dp,
        border = BorderStroke(1.dp, HeaderButtonStroke),
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 18.dp,
                )
        ) {

            Column(
                modifier = Modifier.height(66.dp),
            ) {
                Text(
                    lineHeight = 33.sp,
                    text = service.name,
                    maxLines = 2,
                    fontFamily = nunitoSans,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(
                    text = service.duration.toString() + " " + ComposeString.resource(R.string.minutes)
                        .value(),
                    style = MaterialTheme.typography.h2,
                    fontSize = 14.sp
                )
            }

            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = service.duration.toString(),
                style = TextStyle(
                    color = GreyTextColor,
                    fontFamily = nunitoSans,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp
                )
            )

            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .clip(
                        shape = Shapes.medium
                    )
                    .border(
                        width = 0.dp,
                        color = HeaderButtonStroke,
                        shape = Shapes.medium
                    )
                    .clickable {
                        addService.invoke(service)
                    }
                    .background(
                        color = SupportAnswerBorder
                    )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 34.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        color = Color.White,
                        text = ComposeString.resource(R.string.add).value(),
                        style = TextStyle(
                            fontFamily = nunitoSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    )

                    Image(
                        modifier = Modifier.padding(start = 10.dp),
                        painter = painterResource(R.drawable.add_order),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}