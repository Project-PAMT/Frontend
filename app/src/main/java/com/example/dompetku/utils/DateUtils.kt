package com.example.dompetku.utils

fun convertToYMD(d: String): String {
    // expects d like "d/M/yyyy" or "dd/MM/yyyy"
    val p = d.split("/")
    if (p.size < 3) return d
    val day = p[0].padStart(2, '0')
    val month = p[1].padStart(2, '0')
    val year = p[2]
    return "$year-$month-$day"
}
