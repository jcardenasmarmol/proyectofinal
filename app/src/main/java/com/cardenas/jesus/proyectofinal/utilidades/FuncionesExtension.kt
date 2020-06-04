package com.cardenas.jesus.proyectofinal.utilidades

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Int.twoDigits() =
    if (this <= 9) "0$this" else this.toString()

fun Fragment.setTitle(title: String) {
    (activity as AppCompatActivity).supportActionBar?.title = title
}