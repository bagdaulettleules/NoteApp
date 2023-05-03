package com.example.noteapp.feature_main.domain.use_case

import com.example.noteapp.feature_main.domain.model.Note
import com.example.noteapp.feature_main.domain.repository.NoteLocalRepository
import com.example.noteapp.feature_main.domain.util.OrderBy
import com.example.noteapp.feature_main.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllNotes(
    private val repository: NoteLocalRepository
) {
    operator fun invoke(
        orderBy: OrderBy
    ): Flow<List<Note>> {
        return repository.getAll().map { notes ->
            when (orderBy.orderType) {
                is OrderType.Asc -> {
                    when (orderBy) {
                        is OrderBy.Color -> notes.sortedBy { it.color }
                        is OrderBy.Date -> notes.sortedBy { it.createTs }
                        is OrderBy.Title -> notes.sortedBy { it.title.lowercase() }
                    }
                }
                is OrderType.Desc -> {
                    when (orderBy) {
                        is OrderBy.Color -> notes.sortedByDescending { it.color }
                        is OrderBy.Date -> notes.sortedByDescending { it.createTs }
                        is OrderBy.Title -> notes.sortedByDescending { it.title.lowercase() }
                    }
                }
            }
        }
    }
}