package com.example.wavewash.presentation.account.account_screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.wavewash.presentation.helpers.common.BackButton

@Composable
fun BackAndAvatar (navController: NavController){
    Row (verticalAlignment = Alignment.CenterVertically){
      BackButton(
          onBackClicked = {
              navController.popBackStack()
          }
      )
//      Spacer(modifier = Modifier.weight(1f))
//      Row(verticalAlignment = Alignment.CenterVertically){
//          Text()
//      }
    }
}