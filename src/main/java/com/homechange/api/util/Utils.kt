package com.homechange.api.util

import com.homechange.api.error.ErrorCode
import com.homechange.api.error.HomeException

import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by Nemanja on 5/18/17.

 * Class that contains utility methods, all these methods are static
 * This class is used in entire project
 */
object Utils {

    /**
     * Helper method for date formatting
     * @param date Date for formatting
     * *
     * @return Formatted date as String
     */
    fun formatDate(date: Date?): String {
        val format = SimpleDateFormat("dd-MM-yyyy")
        val formatedDate = format.format(date)
        return formatedDate
    }

    /**
     * Helper method for formatting date and time
     * @param date Date
     * *
     * @return Date and time
     */
    fun formatDateAndTime(date: Date?): String {
        val format = SimpleDateFormat("dd-MM-yyyy HH:mm")
        val formattedDateAndTime = format.format(date)
        return formattedDateAndTime
    }

    /**
     * Method used in home details resource, if both are present, throw error
     * @param param1
     * *
     * @param param2
     */
    @Throws(HomeException::class)
    fun validateBothParamsPresent(param1: String?, param2: Long?) {
        if (param1 != null && param2 != null) {
            throw HomeException("You have to send only one parameter", ErrorCode.INVALID_PARAMETERS)
        }
    }

    /**
     * Method used in home details resource, if both are not present, throw error
     * @param param1
     * *
     * @param param2
     */
    @Throws(HomeException::class)
    fun validateBothParamsNotPresent(param1: String?, param2: Long?) {
        if (param1 == null && param2 == null) {
            throw HomeException("No parameters sent, you have to send home id or username", ErrorCode.INVALID_PARAMETERS)
        }
    }
}
