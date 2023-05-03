package com.example.noteapp.feature_main.domain.use_case

import com.example.noteapp.feature_main.domain.model.Note
import com.example.noteapp.feature_main.domain.repository.NoteLocalRepository

class DeleteNote(
    private val repository: NoteLocalRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.delete(note)
    }
}