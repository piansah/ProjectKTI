package com.alifalpian.krakatauapp.util

fun emptyString(): String {
    return ""
}

fun String.titleCase(): String {
    return replaceFirstChar { char -> char.uppercase() }
}
