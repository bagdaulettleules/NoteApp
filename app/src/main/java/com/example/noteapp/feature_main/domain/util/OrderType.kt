package com.example.noteapp.feature_main.domain.util

sealed class OrderType {
    object Asc : OrderType()
    object Desc : OrderType()
}
