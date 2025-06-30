package com.greenfox.greenfox

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class District(
    val name: String,
    val type: String,
    val resorts: List<Resort>
) : Parcelable

@Parcelize
data class Resort(
    val name: String,
    val type: String,
    val mainImage: Int,
    val options: List<ResortOption>
) : Parcelable

@Parcelize
data class ResortOption(
    val name: String,
    val image: Int
) : Parcelable