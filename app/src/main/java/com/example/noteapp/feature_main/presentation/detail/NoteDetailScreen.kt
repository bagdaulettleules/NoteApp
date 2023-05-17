package com.example.noteapp.feature_main.presentation.detail

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.noteapp.core.util.TestTags
import com.example.noteapp.feature_main.presentation.detail.components.HintTextField
import com.example.noteapp.feature_main.presentation.util.UiEvent
import com.example.noteapp.feature_main.presentation.util.defaultColorSet
import com.example.noteapp.ui.theme.DarkSkyBlue
import com.example.noteapp.ui.theme.NoteAppTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun NoteDetailScreen(
    navController: NavController = rememberNavController(),
    noteColor: Int,
    titleState: TextFieldState,
    contentState: TextFieldState,
    onEvent: (NoteDetailEvent) -> Unit,
    eventFlow: MutableSharedFlow<UiEvent> = MutableSharedFlow()
) {
    val scaffoldState = rememberScaffoldState()
    val backgroundColor = remember {
        Animatable(Color(noteColor))
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { uiEvent ->
            when (uiEvent) {
                is UiEvent.ErrorOccurred -> {
                    scaffoldState.snackbarHostState.showSnackbar(uiEvent.message)
                }

                UiEvent.SuccessfullyCompleted -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(NoteDetailEvent.NoteSaved)
                },
                modifier = Modifier
                    .testTag(TestTags.SAVE_BUTTON),
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save")
            }
        },
        scaffoldState = scaffoldState
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor.value)
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                defaultColorSet.forEach { color ->
                    val selectedColor = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (noteColor == selectedColor) Color.Black else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    backgroundColor.animateTo(
                                        targetValue = Color(selectedColor),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                onEvent(NoteDetailEvent.ColorChanged(selectedColor))
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            HintTextField(
                text = titleState.text,
                hint = titleState.hint,
                isHintVisible = titleState.isHintVisible,
                onValueChange = {
                    onEvent(NoteDetailEvent.TitleChanged(it))
                },
                textStyle = MaterialTheme.typography.h5,
                singleLine = true,
                onFocusChange = {
                    onEvent(NoteDetailEvent.TitleFocusChanged(it))
                },
                testTag = TestTags.TITLE_TEXT_FIELD
            )

            Spacer(modifier = Modifier.height(16.dp))

            HintTextField(
                text = contentState.text,
                hint = contentState.hint,
                isHintVisible = contentState.isHintVisible,
                onValueChange = {
                    onEvent(NoteDetailEvent.ContentChanged(it))
                },
                textStyle = MaterialTheme.typography.body1,
                onFocusChange = {
                    onEvent(NoteDetailEvent.ContentFocusChanged(it))
                },
                testTag = TestTags.CONTENT_TEXT_FIELD
            )

        }
    }
}

@Preview
@Composable
fun DetailScreenPreview() {
    NoteAppTheme {
        NoteDetailScreen(
            noteColor = DarkSkyBlue.toArgb(),
            titleState = TextFieldState("", "Enter title", true),
            contentState = TextFieldState("", "Enter some text ...", true),
            onEvent = {}
        )
    }
}