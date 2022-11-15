package com.example.wavewash.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import com.example.wavewash.data.remote.dto.service.ServiceDto
import com.example.wavewash.domain.model.Service
import com.example.wavewash.domain.model.Washer
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


fun convert(time: Long, duration: Int): String {
    val sdf = SimpleDateFormat("HH:mm")
    val begin = sdf.format(Date(time))
    val end = sdf.format(Date(time + duration * ONE_MINUTE_IN_MILLIS))
    return "$begin-$end"
}

fun durationOfServices(services: List<Service>): Int {
    return services.sumOf {
        it.duration
    }
}

fun priceOfServices(services: List<Service>): Int {
    return services.sumOf {
        it.price
    }
}

fun priceOfJanitorsStake(washers: List<Washer>, price: Int): Int {
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

fun getDateLong(date:String):Long{
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val dateLong = sdf.parse(date)
    return dateLong.time
}

fun Uri.asFile(context: Context): File? {
    context.contentResolver
        .query(this, arrayOf(MediaStore.Images.Media.DATA), null, null, null)
        ?.use { cursor ->
            cursor.moveToFirst()
            val cursorData =
                cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))

            return if (cursorData == null) {
                returnCursorData(this,context)?.let { File(it) }
            } else {
                File(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)))
            }
        }
    return null
}


private fun returnCursorData(uri: Uri?,context: Context): String? {
    if (DocumentsContract.isDocumentUri(context, uri)) {
        val wholeID = DocumentsContract.getDocumentId(uri)
        val splits = wholeID.split(":".toRegex()).toTypedArray()
        if (splits.size == 2) {
            val id = splits[1]
            val column = arrayOf(MediaStore.Images.Media.DATA)
            val sel = MediaStore.Images.Media._ID + "=?"
            val cursor: Cursor? = context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, arrayOf(id), null
            )

            val columnIndex: Int? = cursor?.getColumnIndex(column[0])
            if (cursor?.moveToFirst() == true) {
                return columnIndex?.let { cursor.getString(it) }
            }
            cursor?.close()
        }
    } else {
        return uri?.path
    }
    return null
}