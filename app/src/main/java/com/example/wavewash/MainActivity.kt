package com.example.wavewash

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.wavewash.nav_graphs.NavGraph
import com.example.wavewash.presentation.login.LoginScreen
import com.example.wavewash.ui.theme.AppBackground
import com.example.wavewash.ui.theme.WaveWashTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
//            val openDialogCustom =  remember { mutableStateOf(false) }
//            Button(onClick = {
//                    openDialogCustom.value = true
//                }) {
//
//                }
//                if(openDialogCustom.value){
//                    CalendarPopup(openDialogCustom = openDialogCustom)
//                }

            WaveWashTheme {
                val pagerState = rememberPagerState()
                val navController = rememberNavController()
                val configuration = LocalConfiguration.current

                Scaffold() {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(AppBackground)
                            .padding(horizontal = 24.dp, vertical = 32.dp)
                    ) {
                        NavGraph(pagerState = pagerState, navController = navController, configuration = configuration)
                    }
                }
            }
        }
    }
}
//
//@OptIn(ExperimentalPagerApi::class)
//@Composable
//fun MainScreen() {
//    val pagerState = rememberPagerState()
//    WaveWashTheme {
//        val configuration = LocalConfiguration.current
//        val openDialogCustom =  remember { mutableStateOf(false) }
//
//        Scaffold() {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(AppBackground)
//                    .padding(horizontal = 24.dp, vertical = 32.dp)
//            ) {
//        NavGraph(navController = )
//                ##Popup
//                Button(onClick = {
//                    openDialogCustom.value = true
//                }) {
//
//                }
//                if(openDialogCustom.value){
//                    ChooseServiceDialog(openDialogCustom = openDialogCustom)
//                }


//                LogoNameCompany()
//                LogoCalendar()
//                Tabs(pagerState = pagerState)
//                TabsContent(pagerState = pagerState)

//                JanitorStakeScreen()
//                OverallPriceScreen()
//                NewServiceScreen()
//                NewJanitorScreen()
//                NewOrderScreen()
//                OrderDetails()
//                LoginScreen()
//                ServicesScreen()
//                AccountScreen()
//                JournalActionsScreen()
//                NewPassword()

//                ChatSupport()
//            }
//            /* Add code later */
//        }
//    }
//}
