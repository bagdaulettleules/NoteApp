package com.example.noteapp.feature_main.presentation.util

sealed class Screen(val route: String) {
    object NotesList : Screen("notes_list_screen")
    object NoteDetail : Screen("note_detail_screen")
}
