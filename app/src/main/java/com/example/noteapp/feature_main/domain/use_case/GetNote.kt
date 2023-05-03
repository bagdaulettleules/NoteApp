package com.example.noteapp.feature_main.domain.use_case

import com.example.noteapp.feature_main.domain.model.Note
import com.example.noteapp.feature_main.domain.repository.NoteLocalRepository

class GetNote(
    private val repository: NoteLocalRepository
) {
    suspend operator fun invoke(id: Int) : Note? {
        return repository.getById(id)
    }
}