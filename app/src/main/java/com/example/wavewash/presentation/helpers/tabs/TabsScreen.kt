package com.example.wavewash.presentation.helpers.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wavewash.presentation.helpers.common.LogoNameCompany
import com.example.wavewash.presentation.common.Tabs
import com.example.wavewash.presentation.common.TabsContent
import com.example.wavewash.utils.Screen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsScreen(
    pagerState: PagerState,
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    Column() {
        LogoNameCompany(
            onLogoClick = {
                scope.launch {
                    pagerState.animateScrollToPage(0)
                }

            },
            onCompanyClick = {
                navController.navigate(Screen.AccountScreenRoute.route)
            }
        )
        Tabs(pagerState = pagerState)
        TabsContent(pagerState = pagerState, navController = navController)
    }

}