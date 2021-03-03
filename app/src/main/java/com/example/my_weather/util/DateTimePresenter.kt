package com.example.my_weather.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
class DateTimePresenter(string: String) {
    private val dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(string)!!
    private val cal = Calendar.getInstance()

    private val day: Int
    private val month: String
    private val year: Int
    private val hour: Int
    private val min: String

    private val title: String

    init {
        cal.time = dateTime
        day = cal[Calendar.DAY_OF_MONTH]
        month = when (cal[Calendar.MONTH]) {
            1 -> "Jan"
            2 -> "Feb"
            3 -> "Mar"
            4 -> "Apr"
            5 -> "May"
            6 -> "Jun"
            7 -> "Jul"
            8 -> "Aug"
            9 -> "Sep"
            10 -> "Oct"
            11 -> "Nov"
            else -> "Dec"
        }
        year = cal[Calendar.YEAR]
        hour = cal[Calendar.HOUR_OF_DAY]
        min = if (cal[Calendar.MINUTE] == 0) {
            "00"
        } else {
            cal[Calendar.MINUTE].toString()
        }
        val meridiem = if (hour < 12) {
            "AM"
        } else {
            "PM"
        }

        title = "$month $day, $year $hour:$min $meridiem"
    }

    fun present(): String {
        return title
    }
}
