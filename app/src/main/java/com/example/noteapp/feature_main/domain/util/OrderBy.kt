package com.example.noteapp.feature_main.domain.util

sealed class OrderBy(
    val orderType: OrderType
) {
    class Title(orderType: OrderType) : OrderBy(orderType)
    class Date(orderType: OrderType) : OrderBy(orderType)
    class Color(orderType: OrderType) : OrderBy(orderType)

    fun copy(orderType: OrderType): OrderBy {
        return when(this) {
            is Color -> Color(orderType)
            is Date -> Date(orderType)
            is Title -> Title(orderType)
        }
    }
}
