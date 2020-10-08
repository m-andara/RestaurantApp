package com.example.restaurantapp.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateUtils {

    companion object {
        fun getCurrentDate(): LocalDateTime {

            return LocalDateTime.now()
        }

        fun formatDate(date: LocalDateTime): String {
            val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy h:mm a")
            return dtf.format(date)
        }
    }
}