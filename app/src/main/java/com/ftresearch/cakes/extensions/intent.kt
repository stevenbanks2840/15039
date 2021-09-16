package com.ftresearch.cakes.extensions

import android.content.Intent

fun <T> Intent.requireParcelableExtra(name: String): T =
    getParcelableExtra(name) ?: throw RuntimeException("Intent is not valid, must have $name")
