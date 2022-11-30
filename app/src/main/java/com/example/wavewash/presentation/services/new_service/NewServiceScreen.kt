package com.example.wavewash.presentation.services.new_service

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wavewash.R
import com.example.wavewash.presentation.helpers.common.AddButton
import com.example.wavewash.presentation.helpers.common.BackButton
import com.example.wavewash.presentation.helpers.common.Logo
import com.example.wavewash.presentation.orders.new_order.NewOrderEvent
import com.example.wavewash.presentation.orders.orders_screen.NavigationEvent
import com.example.wavewash.ui.theme.ActiveButtonBackground
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.utils.ComposeString
import com.example.wavewash.utils.REFRESH_SERVICES
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "NewServiceScreen"
@Composable
fun NewServiceScreen(
    navController: NavController,
    viewModel: NewServiceViewModel = hiltViewModel()
) {
    val state = viewModel.state

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is NavigationEvent.GoBack -> {
                    navController.popBackStack()
                }
            }
        }
    }

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Logo()
        Column(
            modifier = Modifier
                .padding(top = 25.dp, bottom = 24.dp)
                .fillMaxWidth()
                .clip(Shapes.large)
                .background(Color.White)
                .padding(start = 24.dp, end = 24.dp, bottom = 58.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = ComposeString.resource(R.string.new_service).value(),
                style = MaterialTheme.typography.body1,
                fontSize = 24.sp
            )
            Text(
                modifier = Modifier.padding(top = 36.dp),
                text = ComposeString.resource(R.string.services_name).value(),
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
                value = state.name,
                onValueChange = { name ->
                    viewModel.onTriggerEvent(NewServiceEvent.ChangeNameValue(name))
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
                modifier = Modifier.padding(top = 36.dp),
                text = ComposeString.resource(R.string.execution_time).value(),
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
                value = state.duration,
                onValueChange = { duration ->
                    if (duration.length < 5) {
                        viewModel.onTriggerEvent(NewServiceEvent.ChangeDurationValue(duration))
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                isError = state.durationError != null
            )
            if (state.durationError != null) {
                Text(
                    text = stringResource(id = state.durationError),
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Text(
                modifier = Modifier.padding(top = 36.dp),
                text = ComposeString.resource(R.string.service_price).value(),
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
                value = state.price,
                onValueChange = { price ->
                    if (price.length < 8) {
                        viewModel.onTriggerEvent(NewServiceEvent.ChangePriceValue(price))
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                isError = state.priceError!=null
            )

            if (state.priceError != null) {
                Text(
                    text = stringResource(id = state.priceError),
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

        }

        Row {
            BackButton(
                onBackClicked = {
                    navController.popBackStack()
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            AddButton(
                onClick = {
                    viewModel.onTriggerEvent(NewServiceEvent.AddService)
                }
            )
        }
    }
}
