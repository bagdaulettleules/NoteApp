package com.example.noteapp.feature_main.presentation.list

import com.example.noteapp.feature_main.domain.model.Note
import com.example.noteapp.feature_main.domain.util.OrderBy

sealed class NotesListEvent {
    data class Order(val orderBy: OrderBy) : NotesListEvent()
    data class Delete(val note: Note) : NotesListEvent()
    object Restore : NotesListEvent()
    data class ToggleOrderSection(val isVisible: Boolean) : NotesListEvent()
}
