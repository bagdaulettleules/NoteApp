package com.example.noteapp.feature_main.presentation.list

import com.example.noteapp.feature_main.domain.model.Note
import com.example.noteapp.feature_main.domain.util.OrderBy
import com.example.noteapp.feature_main.domain.util.OrderType

data class NotesListState(
    val notes: List<Note> = emptyList(),
    val orderBy: OrderBy = OrderBy.Date(OrderType.Desc),
    val isOrderSectionVisible: Boolean = false
)
