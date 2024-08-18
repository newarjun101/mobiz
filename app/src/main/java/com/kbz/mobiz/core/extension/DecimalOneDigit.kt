package com.kbz.mobiz.core.extension

import java.math.RoundingMode
import java.text.DecimalFormat

fun Double?.getOneDecimalValue() : Double {
    val df = DecimalFormat("#.#")
    df.roundingMode = RoundingMode.CEILING
    return df.format(this).toDouble()
}
