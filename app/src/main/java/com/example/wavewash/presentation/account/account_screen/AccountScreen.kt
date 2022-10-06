package com.example.wavewash.presentation.account

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.wavewash.presentation.account.account_screen.components.*
import com.example.wavewash.utils.Screen

@Composable
fun AccountScreen(
    navController: NavController
) {
    Column {
        BackAndAvatar(navController)
        LogoNameLocation()
        JournalActions(
            onClick = {
                navController.navigate(Screen.JournalActionsScreenRoute.route)
            }
        )
        PasswordField(
            onClick = {
                navController.navigate(Screen.NewPasswordScreenRoute.route)
            }
        )
        DarkTheme()
        LanguageRow()
        ChatSupport(
            onClick = {
                navController.navigate(Screen.ChatSupportScreenRoute.route)
            })
        Logout()
    }

}