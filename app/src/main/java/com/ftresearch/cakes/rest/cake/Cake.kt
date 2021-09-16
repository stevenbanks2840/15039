package com.ftresearch.cakes.rest.cake

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cake(
    val title: String?,
    val desc: String?,
    val image: String?
) : Parcelable
