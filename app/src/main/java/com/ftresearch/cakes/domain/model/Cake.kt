package com.ftresearch.cakes.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cake(
    val title: String,
    val desc: String,
    val detail: String,
    val image: String?
) : Parcelable
