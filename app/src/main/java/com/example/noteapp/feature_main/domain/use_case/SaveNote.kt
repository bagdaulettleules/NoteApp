package com.example.noteapp.feature_main.domain.use_case

import com.example.noteapp.feature_main.domain.model.InvalidNoteException
import com.example.noteapp.feature_main.domain.model.Note
import com.example.noteapp.feature_main.domain.repository.NoteLocalRepository

class SaveNote(
    private val repository: NoteLocalRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Title can't be empty")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("Content can't be empty")
        }
        repository.insert(note)
    }
}