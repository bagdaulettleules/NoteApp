package com.example.noteapp.feature_main.presentation.util

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*
import com.example.noteapp.ui.theme.*

val defaultColorSet = setOf(MinionYellow, BurntSienna, DarkSkyBlue, Pistachio, Champagne)

@Composable
fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) {
        mutableStateOf(firstVisibleItemIndex)
    }
    var previousScrollOffset by remember(this) {
        mutableStateOf(firstVisibleItemScrollOffset)
    }

    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}