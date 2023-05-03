package com.example.noteapp.feature_main.data.repository

import com.example.noteapp.feature_main.data.data_source.NoteDao
import com.example.noteapp.feature_main.domain.model.Note
import com.example.noteapp.feature_main.domain.repository.NoteLocalRepository
import kotlinx.coroutines.flow.Flow

class NoteLocalRepositoryImpl(
    private val dao: NoteDao
) : NoteLocalRepository {
    override fun getAll(): Flow<List<Note>> = dao.getAll()

    override suspend fun getById(id: Int): Note? = dao.getById(id)

    override suspend fun insert(note: Note) = dao.insert(note)

    override suspend fun delete(note: Note) = dao.delete(note)

}