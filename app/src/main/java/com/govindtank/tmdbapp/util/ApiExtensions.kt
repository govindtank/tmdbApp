package com.govindtank.tmdbapp.util

import com.govindtank.tmdbapp.app.Constants

fun String?.asUrl(): String = if (this != null) Constants.BUCKET_URL + this else ""