package com.example.wavewash.presentation.orders.new_order

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wavewash.presentation.helpers.common.AddButton
import com.example.wavewash.presentation.helpers.common.BackButton
import com.example.wavewash.presentation.helpers.common.Logo
import com.example.wavewash.presentation.orders.new_order.components.*
import com.example.wavewash.presentation.helpers.popups.ChooseJanitorDialog
import com.example.wavewash.presentation.helpers.popups.janitor.DeleteJanitorDialog
import com.example.wavewash.presentation.helpers.popups.service.ChooseServiceDialog
import com.example.wavewash.presentation.helpers.popups.service.DeleteServiceDialog
import com.example.wavewash.presentation.orders.change_order_screen.ChangeOrderEvent
import com.example.wavewash.presentation.orders.orders_screen.NavigationEvent
import com.example.wavewash.ui.theme.HeaderButtonStroke
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.utils.*
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "NewOrderScreen"
@Composable
fun NewOrderScreen(
    navController: NavController,
    viewModel: NewOrderViewModel  = hiltViewModel()
) {
    val state = viewModel.state
    val openDialogCustomService = remember { mutableStateOf(false) }
    val openDialogCustomJanitor = remember { mutableStateOf(false) }
    val openDeleteDialogJanitor = remember { mutableStateOf(false) }
    val openDeleteDialogService = remember { mutableStateOf(false) }

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
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Logo()
        Column(
            modifier = Modifier
                .padding(top = 25.dp, bottom = 24.dp)
                .fillMaxWidth()
                .clip(Shapes.large)
                .background(Color.White)
                .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 72.dp)
        ) {
            TextMinutes(
                time = state.duration
            )

            ServicePrice(
                serviceError = state.servicesError,
                onClick = {
                    viewModel.onTriggerEvent(NewOrderEvent.GetServices)
                    openDialogCustomService.value = true
                },
                services = state.services,
                price = state.price.toString(),
                onDeleteServicesClick = {
                    openDeleteDialogService.value = true
                }
            )

            if (openDialogCustomService.value) {
                ChooseServiceDialog(
                    openDialogCustom = openDialogCustomService,
                    services = state.servicesOfDialog,
                    addService = { service ->
                        viewModel.onTriggerEvent(NewOrderEvent.ChangeService(service))
                        openDialogCustomService.value = false
                    },
                    isLoading = state.serviceIsLoading,
                    endReached = state.serviceEndIsReached,
                    newServiceClicked = {
                        navController.navigate(Screen.NewServiceScreenRoute.route)
                    },
                    loadMore = {
                        viewModel.onTriggerEvent(NewOrderEvent.LoadMoreServices)
                    },
                    onSearchQueryValue = {
                        viewModel.onTriggerEvent(NewOrderEvent.OnSearchQueryService(it))
                    }
                )
            }

            if (openDeleteDialogService.value) {
                DeleteServiceDialog(
                    onClose = { openDeleteDialogService.value = false },
                    onDeleteClicked = {
                        viewModel.onTriggerEvent(NewOrderEvent.DeleteService(it))
                    },
                    services = state.services
                )
            }

            Divider(
                modifier = Modifier.padding(top = 36.dp),
                color = HeaderButtonStroke,
                thickness = 1.dp
            )

            JanitorStake(
                washerError = state.washersError,
                washerOrderOrNot = state.washerId!=-1L,
                onClick = {
                    viewModel.onTriggerEvent(NewOrderEvent.GetWashers)
                    openDialogCustomJanitor.value = true
                },
                washers = state.washers,
                priceOfJanitorsStake = state.priceOfJanitorsStake.toString(),
                onDeleteWasherClick = {
                    openDeleteDialogJanitor.value = true
                }
            )

            if (openDialogCustomJanitor.value) {
                ChooseJanitorDialog(
                    openDialogCustom = openDialogCustomJanitor,
                    washers = state.washersOfDialog,
                    addWasher = { washer ->
                        viewModel.onTriggerEvent(NewOrderEvent.ChangeWasher(washer))
                        openDialogCustomJanitor.value = false
                    },
                    isLoading = state.washerIsLoading,
                    endReached = state.washerEndIsReached,
                    newJanitorClicked = {
                        navController.navigate(Screen.NewJanitorScreenRoute.route)
                    },
                    loadMore = {
                        viewModel.onTriggerEvent(NewOrderEvent.LoadMoreWashers)
                    },
                    onSearchQueryValue = {
                        viewModel.onTriggerEvent(NewOrderEvent.OnSearchQueryWasher(it))
                    }
                )
            }

            if (openDeleteDialogJanitor.value) {
                DeleteJanitorDialog(
                    onClose = { openDeleteDialogJanitor.value = false },
                    onDeleteClicked = {
                        viewModel.onTriggerEvent(NewOrderEvent.DeleteWasher(it))
                    },
                    janitors = state.washers
                )
            }

            Divider(
                modifier = Modifier.padding(top = 36.dp),
                color = HeaderButtonStroke,
                thickness = 1.dp
            )
            CarModelNumber(
                carNumberError = state.carNumberError,
                carModel = state.carModel,
                carNumber = state.carNumber,
                onChangeCarModelValue = {
                    viewModel.onTriggerEvent(NewOrderEvent.ChangeCarModel(it))
                },
                onChangeCarNumber = {
                    viewModel.onTriggerEvent(NewOrderEvent.ChangeCarNumber(it))
                }
            )

            Divider(
                modifier = Modifier.padding(top = 36.dp),
                color = HeaderButtonStroke,
                thickness = 1.dp
            )
            ClientNumberName(
                clientTelephoneNumberError = state.clientTelephoneNumberError,
                clientNameError = state.clientNameError,
                clientName = state.clientName,
                clientNumber = state.clientNumber,
                onChangeClientName = {
                    viewModel.onTriggerEvent(NewOrderEvent.ChangeClientName(it))
                },
                onChangeClientNumber = {
                    viewModel.onTriggerEvent(NewOrderEvent.ChangeClientNumber(it))
                }
            )
        }

        //buttons
        Row {
            BackButton(
                onBackClicked = {
                    navController.popBackStack()
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            AddButton(
                onClick = {
                    viewModel.onTriggerEvent(NewOrderEvent.AddOrder)
                }
            )
        }
    }
}