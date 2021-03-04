package com.example.my_weather.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
class DateTimePresenter {



    companion object {

        private var day: Int = 0
        private var month: String = ""
        private var year: Int = 0
        private var hour: Int = 0
        private var min: String = ""
        private var meridiem: String = ""

        fun present(dateTime: String, lang: String): String {
            transformString(dateTime, lang)

            return when (lang) {
                "EN" -> "$month $day, $year $hour:$min $meridiem"
                "PT" ->  "$day $month, $year $hour:$min $meridiem"
                else -> "default"
            }
        }

        private fun transformString(dateTimeRaw: String, lang: String) {
            val dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTimeRaw)!!

            val cal = Calendar.getInstance()

            cal.time = dateTime
            day = cal[Calendar.DAY_OF_MONTH]
            month = transformMonth(cal[Calendar.MONTH], lang)
            year = cal[Calendar.YEAR]
            hour = cal[Calendar.HOUR_OF_DAY]
            min = if (cal[Calendar.MINUTE] == 0) {
                "00"
            } else {
                cal[Calendar.MINUTE].toString()
            }
            meridiem = if (hour < 12) {
                "AM"
            } else {
                "PM"
            }
        }

        private fun transformMonth(date: Int, lang: String): String {
            return when (lang) {
                "EN" -> return when (date) {
                    0 -> "Jan"
                    1 -> "Feb"
                    2 -> "Mar"
                    3 -> "Apr"
                    4 -> "May"
                    5 -> "Jun"
                    6 -> "Jul"
                    7 -> "Aug"
                    8 -> "Sep"
                    9 -> "Oct"
                    10 -> "Nov"
                    else -> "Dec"
                }
                "PT" -> return when (date) {
                    0 -> "Jan"
                    1 -> "Fev"
                    2 -> "Mar"
                    3 -> "Abr"
                    4 -> "Mai"
                    5 -> "Jun"
                    6 -> "Jul"
                    7 -> "Ago"
                    8 -> "Set"
                    9 -> "Out"
                    10 -> "Nov"
                    else -> "Dez"
                }
                else -> "Error"
            }
        }

    }
}
