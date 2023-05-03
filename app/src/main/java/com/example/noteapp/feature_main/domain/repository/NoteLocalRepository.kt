package com.example.noteapp.feature_main.domain.repository

import com.example.noteapp.feature_main.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteLocalRepository {

    fun getAll() : Flow<List<Note>>

    suspend fun getById(id: Int): Note?

    suspend fun insert(note: Note)

    suspend fun delete(note: Note)
}