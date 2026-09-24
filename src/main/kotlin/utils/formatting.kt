package Hotel.utils

import java.util.Locale

fun formatCurrency(value: Double): String =
    "%.2f".format(Locale("pt", "BR"), value)
