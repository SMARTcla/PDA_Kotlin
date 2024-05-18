package cz.cvut.fel.sit.pda.screens.transactions

import android.app.DatePickerDialog
import android.content.Context
import java.time.LocalDate
import java.util.Calendar

/**
 * Function to display a date picker dialog to select a date.
 *
 * @param context The context from which the dialog is launched.
 * @param currentDate The current selected date.
 * @param onDateSelected Callback function to be invoked when a date is selected.
 */
fun showDatePicker(context: Context, currentDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    val calendar = Calendar.getInstance().apply {
        set(currentDate.year, currentDate.monthValue - 1, currentDate.dayOfMonth)
    }
    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            onDateSelected(LocalDate.of(year, month + 1, dayOfMonth))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}