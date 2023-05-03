package com.example.noteapp.feature_main.presentation.list

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.noteapp.feature_main.domain.model.Note
import com.example.noteapp.feature_main.presentation.list.components.NoteMinimal
import com.example.noteapp.feature_main.presentation.list.components.OrderBySection
import com.example.noteapp.feature_main.presentation.util.Screen
import com.example.noteapp.feature_main.presentation.util.isScrollingUp
import com.example.noteapp.ui.theme.*
import kotlinx.coroutines.launch


@Composable
fun NotesListScreen(
    navController: NavHostController = rememberNavController(),
    viewState: NotesListState,
    onEvent: (NotesListEvent) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colors.background),
        scaffoldState = scaffoldState,
        floatingActionButton = {
            AnimatedVisibility(
                visible = viewState.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.NoteDetail.route)
                    },
                    shape = RoundedCornerShape(50)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "",
                        tint = MaterialTheme.colors.onSurface
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            val listState = rememberLazyListState()
            val isScrollUp = listState.isScrollingUp()

            if (listState.isScrollInProgress &&
                isScrollUp != viewState.isOrderSectionVisible
            ) {
                onEvent(NotesListEvent.ToggleOrderSection(isScrollUp))
            }

            Text(
                text = "My Notes",
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp),
                color = Color.White,
                style = MaterialTheme.typography.h1
            )

            AnimatedVisibility(
                visible = viewState.isOrderSectionVisible,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderBySection(
                    orderBy = viewState.orderBy,
                    onOrderChange = {
                        onEvent(NotesListEvent.Order(it))
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                state = listState,
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(viewState.notes) { idx, note ->
                    val shape = getShapeByIndex(idx)
                    NoteMinimal(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.NoteDetail.route +
                                            "?noteId=${note.id}&noteColor=${note.color}"
                                )
                            },
                        note = note,
                        shape = shape,
                        onDeleteClick = {
                            onEvent(NotesListEvent.Delete(note))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Note deleted",
                                    actionLabel = "Undo"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    onEvent(NotesListEvent.Restore)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    NoteAppTheme {
        NotesListScreen(
            viewState = NotesListState(
                notes = items,
                isOrderSectionVisible = true
            ),
            onEvent = {}
        )
    }
}

val items = listOf(
    Note("Plan for The Day", "Bla bla bla bla", 3000L, BurntSienna.toArgb(), true, 1),
    Note("Image notes", "Bla bla bla bla", 2000L, MinionYellow.toArgb(), false, 2),
    Note("My Lectures", "Bla bla bla bla", 1000L, Champagne.toArgb(), false, 3),
    Note("ToDo list", "Bla bla bla bla", 4000L, DarkSkyBlue.toArgb(), false, 4)
)

fun getShapeByIndex(index: Int): Shape {
    return when (index % 4) {
        0 -> RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 48.dp,
            bottomStart = 48.dp,
            bottomEnd = 48.dp
        )
        1 -> RoundedCornerShape(
            topStart = 48.dp,
            topEnd = 48.dp,
            bottomStart = 48.dp,
            bottomEnd = 0.dp
        )
        2 -> RoundedCornerShape(
            topStart = 48.dp,
            topEnd = 48.dp,
            bottomStart = 0.dp,
            bottomEnd = 48.dp
        )
        else -> RoundedCornerShape(
            topStart = 48.dp,
            topEnd = 0.dp,
            bottomStart = 48.dp,
            bottomEnd = 48.dp
        )
    }
}