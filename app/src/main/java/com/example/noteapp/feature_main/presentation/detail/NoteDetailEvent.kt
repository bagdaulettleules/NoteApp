package com.example.noteapp.feature_main.presentation.detail

import androidx.compose.ui.focus.FocusState

sealed class NoteDetailEvent {
    data class TitleChanged(val value: String) : NoteDetailEvent()
    data class TitleFocusChanged(val focusState: FocusState) : NoteDetailEvent()
    data class ContentChanged(val value: String) : NoteDetailEvent()
    data class ContentFocusChanged(val focusState: FocusState) : NoteDetailEvent()
    data class ColorChanged(val color: Int) : NoteDetailEvent()
    object NoteSaved : NoteDetailEvent()
}