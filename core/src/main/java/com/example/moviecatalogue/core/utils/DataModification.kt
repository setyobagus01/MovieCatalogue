package com.example.moviecatalogue.core.utils

import java.text.SimpleDateFormat
import java.util.*

object DataModification {
    fun yearConverter(date: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(date)
        val calendar = Calendar.getInstance()
        if (dateFormat != null) {
            calendar.time = dateFormat
        }

        return calendar.get(Calendar.YEAR).toString()
    }
}