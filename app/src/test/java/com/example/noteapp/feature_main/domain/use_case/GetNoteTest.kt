package com.example.noteapp.feature_main.domain.use_case

import com.example.noteapp.feature_main.data.repository.DummyLocalRepository
import com.example.noteapp.feature_main.domain.model.Note
import com.example.noteapp.feature_main.domain.repository.NoteLocalRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetNoteTest {
    private lateinit var repository: NoteLocalRepository
    private lateinit var getNote: GetNote
    private lateinit var note: Note

    @Before
    fun setUp() = runBlocking {
        repository = DummyLocalRepository()
        getNote = GetNote(repository)
        note = Note(
            title = "To be deleted",
            content = "Note to be deleted",
            createTs = 1L,
            color = 1,
            id = 1
        )

        repository.insert(note)
    }

    @Test
    fun `Get existing note`() = runBlocking {
        val retrievedNote = getNote(note.id!!)

        assertEquals(retrievedNote, note)
    }

    @Test
    fun `Get not existing note`() = runBlocking {
        val retrievedNote = getNote(132)

        assertNull(retrievedNote)
    }
}