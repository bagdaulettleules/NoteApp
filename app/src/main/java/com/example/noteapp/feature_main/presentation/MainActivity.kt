package com.example.noteapp.feature_main.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.noteapp.feature_main.presentation.detail.NoteDetailScreen
import com.example.noteapp.feature_main.presentation.detail.NoteDetailViewModel
import com.example.noteapp.feature_main.presentation.list.NotesListScreen
import com.example.noteapp.feature_main.presentation.list.NotesListViewModel
import com.example.noteapp.feature_main.presentation.util.Screen
import com.example.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesList.route
                    ) {
                        composable(route = Screen.NotesList.route) {
                            val viewModel = hiltViewModel<NotesListViewModel>()
                            NotesListScreen(
                                navController = navController,
                                viewState = viewModel.state.value,
                                onEvent = viewModel::onEvent
                            )
                        }

                        composable(
                            route = Screen.NoteDetail.route +
                                    "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(name = "noteId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(name = "noteColor") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            val viewModel = hiltViewModel<NoteDetailViewModel>()
                            val noteColor = it.arguments?.getInt("noteColor") ?: viewModel.color.value
                            val titleState = viewModel.title
                            val contentState = viewModel.content
                            NoteDetailScreen(
                                navController = navController,
                                noteColor = noteColor,
                                titleState = titleState.value,
                                contentState = contentState.value,
                                onEvent = viewModel::onEvent,
                                eventFlow = viewModel.eventFlow
                            )
                        }
                    }
                }
            }
        }
    }
}