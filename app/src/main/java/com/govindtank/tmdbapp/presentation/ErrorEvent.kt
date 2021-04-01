package com.govindtank.tmdbapp.presentation

data class ErrorEvent(
    val msg: String,
    val actionMsg: String? = null,
    val action: (() -> Unit)? = null
)