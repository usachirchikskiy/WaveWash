package com.example.wavewash.nav_graphs

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalPagerApi
@Composable
fun NavGraph(pagerState: PagerState, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "Main_Graph_Route"
    ) {
        mainGraph(pagerState = pagerState, navController = navController)
    }
}