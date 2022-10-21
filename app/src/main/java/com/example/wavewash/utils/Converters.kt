package com.example.wavewash.utils

import android.util.Log
import com.example.wavewash.data.remote.dto.service.ServiceAnswerDto
import com.example.wavewash.data.remote.dto.washer.WasherAnswerDto
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


fun convert(time: Long, duration: Int): String {
    val sdf = SimpleDateFormat("HH:mm")
    val begin = sdf.format(Date(time))
    val end = sdf.format(Date(time + duration * ONE_MINUTE_IN_MILLIS))
    return "$begin-$end"
}

fun durationOfServices(services: List<ServiceAnswerDto>): Int {
    return services.sumOf {
        it.duration
    }
}

fun priceOfServices(services: List<ServiceAnswerDto>): Int {
    return services.sumOf {
        it.price
    }
}

fun priceOfJanitorsStake(washers: List<WasherAnswerDto>, price: Int): Int {
    val size = washers.size
    if (size > 0) {
        val maxstake = washers.maxOf {
            it.stake
        }
        return (price * maxstake) / 100
    }
    return 0
}

fun getDate(): String {
    val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
    return dateFormatter.format(Date())
}

fun getNextDate(date: String): String {
    val dateParser = SimpleDateFormat("yyyy-MM-dd")
    val parse = dateParser.parse(date)
    val nextDate = Date(parse.time + 1440 * ONE_MINUTE_IN_MILLIS)
    return dateParser.format(nextDate)
}

fun getPreviousDate(date: String): String {
    val dateParser = SimpleDateFormat("yyyy-MM-dd")
    val parse = dateParser.parse(date)
    val previousDate = Date(parse.time - 1440 * ONE_MINUTE_IN_MILLIS)
    return dateParser.format(previousDate)
}

fun getFromAndTo(): Pair<String, String> {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val dateFrom = Date()
    val dateTo = Date(dateFrom.time + 1440 * ONE_MINUTE_IN_MILLIS)
    return Pair(sdf.format(dateFrom), sdf.format(dateTo))
}

fun getDateOnRuLang(date: String): String {
    val dateParser = SimpleDateFormat("yyyy-MM-dd")
    val date = dateParser.parse(date)
    val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
    return dateFormatter.format(date)
}

fun getDifferenceBetweenDates(dateFrom: String, dateTo: String): Long {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val dateBegin = sdf.parse(dateFrom)
    val dateEnd = sdf.parse(dateTo)
    val diff = dateEnd.time - dateBegin.time
    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
}

//object Devices {
//    const val DEFAULT = ""
//    const val NEXUS_7 = "id:Nexus 7"
//    const val NEXUS_7_2013 = "id:Nexus 7 2013"
//    const val NEXUS_5 = "id:Nexus 5"
//    const val NEXUS_6 = "id:Nexus 6"
//    const val NEXUS_9 = "id:Nexus 9"
//    const val NEXUS_10 = "name:Nexus 10"
//    const val NEXUS_5X = "id:Nexus 5X"
//    const val NEXUS_6P = "id:Nexus 6P"
//    const val PIXEL_C = "id:pixel_c"
//    const val PIXEL = "id:pixel"
//    const val PIXEL_XL = "id:pixel_xl"
//    const val PIXEL_2 = "id:pixel_2"
//    const val PIXEL_2_XL = "id:pixel_2_xl"
//    const val PIXEL_3 = "id:pixel_3"
//    const val PIXEL_3_XL = "id:pixel_3_xl"
//    const val PIXEL_3A = "id:pixel_3a"
//    const val PIXEL_3A_XL = "id:pixel_3a_xl"
//    const val PIXEL_4 = "id:pixel_4"
//    const val PIXEL_4_XL = "id:pixel_4_xl"
//    const val AUTOMOTIVE_1024p = "id:automotive_1024p_landscape"
//}