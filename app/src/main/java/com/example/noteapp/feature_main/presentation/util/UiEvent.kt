package com.example.noteapp.feature_main.presentation.util

sealed class UiEvent {
    data class ErrorOccurred(val message: String): UiEvent()
    object SuccessfullyCompleted : UiEvent()
}
