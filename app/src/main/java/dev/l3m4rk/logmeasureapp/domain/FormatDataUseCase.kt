package dev.l3m4rk.logmeasureapp.domain

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class FormatDataUseCase @Inject constructor() {

    private val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    operator fun invoke(date: Long): String {
        return formatter.format(Date(date))
    }
}