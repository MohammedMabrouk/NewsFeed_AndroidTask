package com.mohamedmabrouk.newsfeeds.utils

import android.net.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateFormat {
    val ARTICLE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    val LAYOUT_DATE_FORMAT = "MMMM dd, yyyy"

    fun changeDateFormat(
        dateInString: String?,
        inputFormatStr: String?,
        outputFormatStr: String?
    ): String? {
        val inputFormat = SimpleDateFormat(inputFormatStr, Locale.ENGLISH)
        val outputFormat = SimpleDateFormat(outputFormatStr, Locale.ENGLISH)
        val date: Date
        var str: String? = null
        try {
            date = inputFormat.parse(dateInString)
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        } catch (e: java.text.ParseException) {
            e.printStackTrace()
        }
        return str
    }
}