package com.example.noteapp.feature_main.domain.use_case

data class NoteUseCases(
    val getAll: GetAllNotes,
    val get: GetNote,
    val save: SaveNote,
    val delete: DeleteNote
)