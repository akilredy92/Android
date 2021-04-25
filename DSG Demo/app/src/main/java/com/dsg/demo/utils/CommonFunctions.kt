package com.dsg.demo.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class CommonFunctions
{
    fun convertStringToLocalDate(dateFormatter: String?, strDate: String?): Date? {
        val cal = Calendar.getInstance()
        val tz = cal.timeZone
        val dateFormat = SimpleDateFormat(dateFormatter)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        var date: Date? = null

        try
        {
            date = dateFormat.parse(strDate)
            val dateFor = SimpleDateFormat(dateFormatter)
            dateFor.timeZone = tz
            val dt = dateFor.format(date)
            date = dateFor.parse(dt)
        } catch (e: ParseException) {
        }
        return date
    }
}