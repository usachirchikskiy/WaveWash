package com.example.wavewash.presentation.orders.change_order_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.wavewash.R
import com.example.wavewash.presentation.helpers.common.AddButton
import com.example.wavewash.presentation.helpers.common.BackButton
import com.example.wavewash.presentation.orders.change_order_screen.components.CancelButton
import com.example.wavewash.presentation.orders.change_order_screen.components.SaveButton
import com.example.wavewash.presentation.helpers.common.Logo
import com.example.wavewash.presentation.helpers.popups.ChooseJanitorDialog
import com.example.wavewash.presentation.helpers.popups.janitor.DeleteJanitorDialog
import com.example.wavewash.presentation.helpers.popups.order.CancelDialog
import com.example.wavewash.presentation.helpers.popups.service.ChooseServiceDialog
import com.example.wavewash.presentation.helpers.popups.service.DeleteServiceDialog
import com.example.wavewash.presentation.orders.new_order.NewOrderEvent
import com.example.wavewash.presentation.orders.new_order.components.*
import com.example.wavewash.presentation.orders.orders_screen.NavigationEvent
import com.example.wavewash.presentation.orders.orders_screen.OrdersEvent
import com.example.wavewash.ui.theme.HeaderButtonStroke
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.utils.*
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "ChangeOrderScreen"
@Composable
fun ChangeOrderScreen(
    navController: NavController,
    viewModel: ChangeOrderViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val openDialogCustomService = remember { mutableStateOf(false) }
    val openDialogCustomJanitor = remember { mutableStateOf(false) }
    val openDeleteDialogJanitor = remember { mutableStateOf(false) }
    val openDeleteDialogService = remember { mutableStateOf(false) }
    val cancelDialogOrder = remember { mutableStateOf(false) }

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
                .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 72.dp)
        ) {
            TextMinutes(
                text = ComposeString.resource(R.string.change_of_order).value(),
                time = state.duration
            )

            ServicePrice(
                serviceError = state.servicesError,
                onClick = {
                    viewModel.onTriggerEvent(ChangeOrderEvent.GetServices)
                    openDialogCustomService.value = true
                },
                services = state.services,
                price = state.price.toString(),
                onDeleteServicesClick = {
                    openDeleteDialogService.value = true
                }
            )

            Divider(
                modifier = Modifier.padding(top = 36.dp),
                color = HeaderButtonStroke,
                thickness = 1.dp
            )

            JanitorStake(
                washerError = state.washersError,
                washerOrderOrNot = false,
                onClick = {
                    viewModel.onTriggerEvent(ChangeOrderEvent.GetWashers)
                    openDialogCustomJanitor.value = true
                },
                washers = state.washers,
                priceOfJanitorsStake = state.priceOfJanitorsStake.toString(),
                onDeleteWasherClick = {
                    openDeleteDialogJanitor.value = true
                }
            )

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
                    viewModel.onTriggerEvent(ChangeOrderEvent.ChangeCarModel(it))
                },
                onChangeCarNumber = {
                    viewModel.onTriggerEvent(ChangeOrderEvent.ChangeCarNumber(it))
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
                clientNumber = state.clientNumber,
                clientName = state.clientName,
                onChangeClientName = {
                    viewModel.onTriggerEvent(ChangeOrderEvent.ChangeClientName(it))
                },
                onChangeClientNumber = {
                    viewModel.onTriggerEvent(ChangeOrderEvent.ChangeClientNumber(it))
                }
            )

        }

        //popups
        if (openDialogCustomService.value) {
            ChooseServiceDialog(
                openDialogCustom = openDialogCustomService,
                services = state.servicesOfDialog,
                addService = { service ->
                    viewModel.onTriggerEvent(ChangeOrderEvent.ChangeService(service))
                    openDialogCustomService.value = false
                },
                isLoading = state.serviceIsLoading,
                endReached = state.serviceEndIsReached,
                newServiceClicked = {
                    navController.navigate(Screen.NewServiceScreenRoute.route)
                },
                loadMore = {
                    viewModel.onTriggerEvent(ChangeOrderEvent.LoadMoreServices)
                },
                onSearchQueryValue = {
                    viewModel.onTriggerEvent(ChangeOrderEvent.OnSearchQueryService(it))
                }
            )
        }

        if (openDeleteDialogService.value) {
            DeleteServiceDialog(
                onClose = { openDeleteDialogService.value = false },
                onDeleteClicked = {
                    viewModel.onTriggerEvent(ChangeOrderEvent.DeleteService(it))
                },
                services = state.services
            )
        }

        if (openDialogCustomJanitor.value) {
            ChooseJanitorDialog(
                openDialogCustom = openDialogCustomJanitor,
                washers = state.washersOfDialog,
                addWasher = { washer ->
                    viewModel.onTriggerEvent(ChangeOrderEvent.ChangeWasher(washer))
                    openDialogCustomJanitor.value = false
                },
                isLoading = state.washerIsLoading,
                endReached = state.washerEndIsReached,
                newJanitorClicked = {
                    navController.navigate(Screen.NewJanitorScreenRoute.route)
                },
                loadMore = {
                    viewModel.onTriggerEvent(ChangeOrderEvent.LoadMoreWashers)
                },
                onSearchQueryValue = {
                    viewModel.onTriggerEvent(ChangeOrderEvent.OnSearchQueryWasher(it))
                }
            )
        }

        if (openDeleteDialogJanitor.value) {
            DeleteJanitorDialog(
                onClose = { openDeleteDialogJanitor.value = false },
                onDeleteClicked = {
                    viewModel.onTriggerEvent(ChangeOrderEvent.DeleteWasher(it))
                },
                janitors = state.washers
            )
        }

        if (cancelDialogOrder.value) {
            CancelDialog(
                openDialogCustom = cancelDialogOrder,
                onBackClicked = {
                    cancelDialogOrder.value = false
                },
                onCancelClicked = {
                    cancelDialogOrder.value = false
                    viewModel.onTriggerEvent(ChangeOrderEvent.CancelOrder(it))
                }
            )
        }

//        if (state.changeCompleted) {
//            viewModel.onTriggerEvent(ChangeOrderEvent.Back)
//            navController.previousBackStackEntry
//                ?.savedStateHandle
//                ?.set(REFRESH_ORDER, REFRESH_ORDER)
//            navController.popBackStack()
//        }

        //buttons
        Row {
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                BackButton(
                    onBackClicked = {
                        navController.popBackStack()
                    }
                )

                CancelButton(
                    onClick = {
                        cancelDialogOrder.value = true
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            SaveButton(
                onClick = {
                    viewModel.onTriggerEvent(ChangeOrderEvent.SaveChanges)
                }
            )
        }
    }
}
