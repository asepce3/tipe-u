package com.cahyana.asep.tipe_u.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

object CurrencyHelper {

    fun toRupiah(value: Long): String {
        val rupiahFormat: DecimalFormat = DecimalFormat.getCurrencyInstance() as DecimalFormat
        val formatSymbol = DecimalFormatSymbols()

        formatSymbol.currencySymbol = "Rp. "
        formatSymbol.monetaryDecimalSeparator = ','
        formatSymbol.groupingSeparator = '.'

        rupiahFormat.decimalFormatSymbols = formatSymbol

        return rupiahFormat.format(value)
    }
}
