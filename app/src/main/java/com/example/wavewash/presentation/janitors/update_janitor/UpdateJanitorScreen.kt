package com.example.wavewash.presentation.janitors.update_janitor

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wavewash.R
import com.example.wavewash.presentation.helpers.common.AddButton
import com.example.wavewash.presentation.helpers.common.BackButton
import com.example.wavewash.presentation.helpers.common.Logo
import com.example.wavewash.presentation.helpers.common.SaveButton
import com.example.wavewash.presentation.janitors.new_janitor.NewJanitorEvents
import com.example.wavewash.presentation.orders.orders_screen.NavigationEvent
import com.example.wavewash.ui.theme.ActiveButtonBackground
import com.example.wavewash.ui.theme.HeaderButtonColor
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.ui.theme.nunitoSans
import com.example.wavewash.utils.ComposeString
import com.example.wavewash.utils.REFRESH_WASHER
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun UpdateJanitorScreen(
    navController: NavController,
    viewModel: UpdateJanitorViewModel = hiltViewModel()
) {
    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    val state = viewModel.state
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                viewModel.onTriggerEvent(UpdateJanitorEvents.GalleryImage(uri))
            }
        }
    )

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is NavigationEvent.GoBack -> {
                    navController.popBackStack()
                }
            }
        }
    }

    if (state.isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Logo()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp, bottom = 24.dp)
                .clip(Shapes.large)
                .background(Color.White)
                .padding(start = 24.dp, bottom = 58.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = ComposeString.resource(R.string.update_janitor).value(),
                style = MaterialTheme.typography.body1,
                fontSize = 24.sp
            )

            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .padding(top = 44.dp)
            ) {

                CoilImage(
                    modifier = Modifier
                        .width(248.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            if (cameraPermissionState.hasPermission) {
                                imagePicker.launch("image/*")
                            } else {
                                cameraPermissionState.launchPermissionRequest()
                            }
                        },
                    imageModel = state.uri ?: state.imageUrl,
                    contentScale = ContentScale.Crop,

                    shimmerParams = ShimmerParams(
                        baseColor = Color.White,
                        highlightColor = Color.LightGray,
                        durationMillis = 350,
                        dropOff = 0.65f,
                        tilt = 20f
                    ),
                    // shows an error text message when request failed.
                    failure = {
                        Box(
                            modifier = Modifier
                                .background(Color(0XFFEFF1F8))
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.error_image),
                                contentDescription = "error_loading"
                            )
                        }
                    }
                )

                Column(
                    modifier = Modifier
                        .padding(start = 36.dp, end = 24.dp)
                ) {
                    Text(
                        text = ComposeString.resource(R.string.name).value(),
                        style = TextStyle(
                            color = HeaderButtonColor,
                            fontFamily = nunitoSans,
                            fontWeight = FontWeight.Normal,
                            lineHeight = 0.sp,
                            fontSize = 14.sp,
                        ),
                        color = Color(0xFF303972),
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = ActiveButtonBackground,
                            focusedBorderColor = Color(0xFFD3DDEC),
                            unfocusedBorderColor = Color(0xFFD3DDEC),
                        ),
                        singleLine = true,
                        value = state.name,
                        onValueChange = { text ->
                            viewModel.onTriggerEvent(UpdateJanitorEvents.ChangeNameValue(text))
                        },
                        isError = state.nameError!=null
                    )
                    if (state.nameError != null) {
                        Text(
                            text = stringResource(id = state.nameError),
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }

                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = ComposeString.resource(R.string.telephone_number).value(),
                        style = MaterialTheme.typography.h2,
                        fontSize = 14.sp,
                        color = Color(0xFF303972)
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = ActiveButtonBackground,
                            focusedBorderColor = Color(0xFFD3DDEC),
                            unfocusedBorderColor = Color(0xFFD3DDEC),
                        ),
                        singleLine = true,
                        value = state.telephoneNumber,
                        onValueChange = { telephoneNumber ->
                            if (telephoneNumber.length < 10) {
                                viewModel.onTriggerEvent(
                                    UpdateJanitorEvents.ChangePhoneNumberValue(
                                        telephoneNumber
                                    )
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        leadingIcon = {
                            Text(
                                text = "+998"
                            )
                        },
                        isError = state.telephoneNumberError!=null
                    )
                    if (state.telephoneNumberError != null) {
                        Text(
                            text = stringResource(id = state.telephoneNumberError),
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }

                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = ComposeString.resource(R.string.janitor_stake_percent).value(),
                        style = MaterialTheme.typography.h2,
                        fontSize = 14.sp,
                        color = Color(0xFF303972)
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = ActiveButtonBackground,
                            focusedBorderColor = Color(0xFFD3DDEC),
                            unfocusedBorderColor = Color(0xFFD3DDEC),
                        ),
                        singleLine = true,
                        value = state.stake,
                        onValueChange = { stake ->
                            if (stake.length <= 2) viewModel.onTriggerEvent(
                                UpdateJanitorEvents.ChangeStakeValue(
                                    stake
                                )
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        isError = state.stakeError!=null
                    )

                    if (state.stakeError != null) {
                        Text(
                            text = stringResource(id = state.stakeError),
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                }
            }
        }

        Row {
            BackButton(
                onBackClicked = {
                    navController.popBackStack()
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            SaveButton(
                onClick = {
                    viewModel.onTriggerEvent(UpdateJanitorEvents.UpdateWasher)
                }
            )
        }
    }
}