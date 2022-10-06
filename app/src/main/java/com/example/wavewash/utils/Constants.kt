package com.example.wavewash.utils

import com.example.wavewash.R

val BASE_URL = "http://192.168.1.19:8090/"
val ERROR_LOGIN = "error in login"
val SUCCESS_LOGIN = "success in login"
val EMAIL_KEY = "email_key"
val PASSWORD_KEY = "password_key"
val TOKEN_KEY = "token"
val REFRESH = "refresh"

val tabs = listOf(
    TabItem.Orders,
    TabItem.Janitors,
    TabItem.Services,
    TabItem.Analytics,
)

// Orders
val headerOrderButtons = listOf(
    ScreenHeader(R.drawable.add_order) { ComposeString.resource(R.string.add_order).value() },
    ScreenHeader(R.drawable.export) { ComposeString.resource(R.string.export).value() }
)

//Tabs
val ordersTab = listOf(
    OrdersScreenTab.New,
    OrdersScreenTab.Done,
)

//Janitor
val headerJanitorButtons = listOf(
    ScreenHeader(R.drawable.add_order) { ComposeString.resource(R.string.new_janitor).value() },
//    ScreenHeader(R.drawable.export) { ComposeString.resource(R.string.export).value() }
)

//Services
val headerServiceButtons = listOf(
    ScreenHeader(R.drawable.add_order) { ComposeString.resource(R.string.new_service).value() },
    ScreenHeader(R.drawable.export) { ComposeString.resource(R.string.export).value() }
)

//OverallPrice
val headerOverallButtons = listOf(
    ScreenHeader(R.drawable.export) { ComposeString.resource(R.string.export).value() }
)

//GridCellSize
val gridCellsSize = 3