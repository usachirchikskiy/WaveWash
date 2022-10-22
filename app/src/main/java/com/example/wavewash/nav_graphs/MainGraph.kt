package com.example.wavewash.nav_graphs

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.wavewash.presentation.orders.change_order_screen.ChangeOrderScreen
import com.example.wavewash.presentation.account.AccountScreen
import com.example.wavewash.presentation.account.chat_support.ChatSupportScreen
import com.example.wavewash.presentation.janitors.janitor_details.JanitorDetailsScreen
import com.example.wavewash.presentation.analytics.janitor_stake.JanitorStakeScreen
import com.example.wavewash.presentation.account.journal_actions.JournalActionsScreen
import com.example.wavewash.presentation.janitors.new_janitor.NewJanitorScreen
import com.example.wavewash.presentation.orders.new_order.NewOrderScreen
import com.example.wavewash.presentation.services.new_service.NewServiceScreen
import com.example.wavewash.presentation.orders.order_details_screen.OrderDetailsScreen
import com.example.wavewash.presentation.analytics.overall_price.OverallPriceScreen
import com.example.wavewash.presentation.account.password.NewPasswordScreen
import com.example.wavewash.presentation.helpers.tabs.TabsScreen
import com.example.wavewash.presentation.login.LoginScreen
import com.example.wavewash.presentation.services.update_service.UpdateServiceScreen
import com.example.wavewash.utils.Screen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalPagerApi
fun NavGraphBuilder.mainGraph(
    pagerState: PagerState,
    navController: NavController
) {
    navigation(
        startDestination = Screen.LoginScreenRoute.route,//Screen.LoginScreenRoute.route
        route = "Main_Graph_Route"
    ) {
        //Login
        composable(route = Screen.LoginScreenRoute.route) {
            LoginScreen(navController)
        }

        //Main
        composable(Screen.MainScreenRoute.route) {
            TabsScreen(pagerState, navController)
        }

        //OrdersNavigation
        composable(Screen.NewOrderScreenRoute.route) {
            NewOrderScreen(navController)
        }

        composable(
            Screen.OrderDetailsScreenRoute.route + "/{orderId}",
            arguments = listOf(navArgument("orderId") {
                type = NavType.LongType
            })
        ) {
            OrderDetailsScreen(navController)
        }

        composable(
            Screen.ChangeOrderScreenRoute.route + "/{orderId}",
            arguments = listOf(navArgument("orderId") {
                type = NavType.LongType
            })
        ) {
            ChangeOrderScreen(navController)
        }

        //JanitorsNavigation
        composable(
            Screen.JanitorDetailsScreenRoute.route + "/{washerId}",
            arguments = listOf(navArgument("washerId") {
                type = NavType.LongType
            })
        ) {
            JanitorDetailsScreen(
                navController
            )
        }

        composable(Screen.NewJanitorScreenRoute.route) {
            NewJanitorScreen(navController)
        }

        //ServicesNavigation
        composable(Screen.NewServiceScreenRoute.route) {
            NewServiceScreen(navController)
        }

        composable(
            Screen.UpdateServiceScreen.route + "/{serviceId}",
            arguments = listOf(navArgument("serviceId") {
                type = NavType.LongType
            })
        ) {
            UpdateServiceScreen(navController)
        }

        //AnalyticsNavigation
        composable(Screen.OverallPriceScreenRoute.route) {
            OverallPriceScreen(navController)
        }

        composable(Screen.JanitorStakeScreenRoute.route) {
            JanitorStakeScreen(navController)
        }

        //AccountNavigation
        composable(Screen.AccountScreenRoute.route) {
            AccountScreen(navController)
        }

        composable(Screen.JournalActionsScreenRoute.route) {
            JournalActionsScreen(navController)
        }

        composable(Screen.NewPasswordScreenRoute.route) {
            NewPasswordScreen(navController)
        }

        composable(Screen.ChatSupportScreenRoute.route) {
            ChatSupportScreen(navController)
        }

    }
}