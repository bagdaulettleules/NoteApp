package com.example.noteapp.feature_main.data.repository

import com.example.noteapp.feature_main.domain.model.Note
import com.example.noteapp.feature_main.domain.repository.NoteLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DummyLocalRepository : NoteLocalRepository {

    private val notes = mutableListOf<Note>()

    override fun getAll(): Flow<List<Note>> {
        return flow { emit(notes) }
    }

    override suspend fun getById(id: Int): Note? {
        return notes.find { it.id == id }
    }

    override suspend fun insert(note: Note) {
        notes.add(note)
    }

    override suspend fun delete(note: Note) {
        notes.remove(note)
    }
}