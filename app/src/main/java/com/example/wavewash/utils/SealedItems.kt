package com.example.wavewash.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.wavewash.R
import com.example.wavewash.presentation.analytics.AnalyticsScreen
import com.example.wavewash.presentation.janitors.janitors_screen.JanitorsScreen
import com.example.wavewash.presentation.orders.OrdersScreen
import com.example.wavewash.presentation.services.services_screen.ServicesScreen

typealias ComposableFun = @Composable (navController: NavController) -> Unit
typealias ComposableString = @Composable () -> String

data class ScreenHeader(val icon: Int, val buttonText: ComposableString)

sealed class TabItem(val title: ComposableString, val screen: ComposableFun) {
    @RequiresApi(Build.VERSION_CODES.O)
    object Orders : TabItem({ ComposeString.resource(R.string.orders).value() }, { navController->
        OrdersScreen(navController)
    })
    object Janitors : TabItem({ ComposeString.resource(R.string.janitors).value() }, { navController->
        JanitorsScreen(navController)
    })
    object Services : TabItem({ ComposeString.resource(R.string.services).value() }, { navController->
        ServicesScreen(navController)
    })
    object Analytics : TabItem({ ComposeString.resource(R.string.analytics).value() }, { navController->
        AnalyticsScreen(navController)
    })
}

sealed class OrdersScreenTab(val title: Int){
    object New:OrdersScreenTab(R.string.active_orders)
    object Done:OrdersScreenTab(R.string.done_order_tab)
}

sealed class Screen(val route:String){
    object MainScreenRoute:Screen("MainScreenRoute")

    //OrdersGraph
    object NewOrderScreenRoute:Screen("NewOrderScreenRoute")
    object OrderDetailsScreenRoute:Screen("OrderDetailsScreenRoute")
    object ChangeOrderScreenRoute:Screen("ChangeOrderScreenRoute")

    //JanitorGraph
    object JanitorDetailsScreenRoute:Screen("JanitorDetailsScreenRoute")
    object NewJanitorScreenRoute:Screen("NewJanitorScreenRoute")

    //ServicesGraph
    object NewServiceScreenRoute:Screen("NewServiceScreenRoute")
    object UpdateServiceScreen:Screen("UpdateServiceScreen")

    //AnalyticsGraph
    object JanitorStakeScreenRoute:Screen("JanitorStakeScreenRoute")
    object OverallPriceScreenRoute:Screen("OverallPriceScreenRoute")

    //AccountGraph
    object AccountScreenRoute:Screen("AccountScreenRoute")
    object JournalActionsScreenRoute:Screen("JournalActionsScreenRoute")
    object NewPasswordScreenRoute:Screen("NewPasswordScreenRoute")
    object ChatSupportScreenRoute:Screen("ChatSupportScreenRoute")

    //LoginGraph
    object LoginScreenRoute:Screen("LoginScreenRoute")
}