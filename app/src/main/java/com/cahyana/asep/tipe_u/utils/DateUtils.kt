package com.cahyana.asep.tipe_u.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private fun dateFormatter(): String {
        val formatter = SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH)
        return formatter.format(Date())
    }
    fun getCurrentDate() = dateFormatter()
}