package com.example.noteapp.feature_main.domain.use_case

import com.example.noteapp.feature_main.data.repository.DummyLocalRepository
import com.example.noteapp.feature_main.domain.model.Note
import com.example.noteapp.feature_main.domain.repository.NoteLocalRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DeleteNoteTest {
    private lateinit var repository: NoteLocalRepository
    private lateinit var deleteNote: DeleteNote
    private lateinit var note: Note

    @Before
    fun setUp() {
        repository = DummyLocalRepository()
        deleteNote = DeleteNote(repository)
        note = Note(
            title = "To be deleted",
            content = "Note to be deleted",
            createTs = 1L,
            color = 1,
            id = 1
        )

        runBlocking {
            repository.insert(note)
        }
    }

    @Test
    fun `Deleting note`() = runBlocking {
        deleteNote(note)
        val retrievedNote = repository.getById(note.id!!)

        assertNull(retrievedNote)
    }
}