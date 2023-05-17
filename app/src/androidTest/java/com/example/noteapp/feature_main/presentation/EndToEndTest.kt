package com.example.noteapp.feature_main.presentation

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.test.platform.app.InstrumentationRegistry
import com.example.noteapp.R
import com.example.noteapp.core.util.TestTags
import com.example.noteapp.di.AppModule
import com.example.noteapp.feature_main.presentation.detail.NoteDetailScreen
import com.example.noteapp.feature_main.presentation.detail.NoteDetailViewModel
import com.example.noteapp.feature_main.presentation.list.NotesListScreen
import com.example.noteapp.feature_main.presentation.list.NotesListViewModel
import com.example.noteapp.feature_main.presentation.util.Screen
import com.example.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class EndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            NoteAppTheme {
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

    @Test
    fun addEditNote() {
        val noteTitle = "test-title"
        val noteContent = "test-content"

        // add note
        composeRule.onNodeWithTag(TestTags.ADD_BUTTON).performClick()
        composeRule
            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput(noteTitle)
        composeRule
            .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
            .performTextInput(noteContent)
        composeRule.onNodeWithTag(TestTags.SAVE_BUTTON).performClick()

        // is visible in list
        composeRule.onNodeWithText(noteTitle).assertIsDisplayed()
        composeRule.onNodeWithText(noteContent).assertIsDisplayed()

        composeRule.onNodeWithTag(TestTags.LIST_ITEM).performClick()

        // edit note
        //TODO weird bug in case running only this test while editing title clears and writes new
        composeRule
            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("-new")
        composeRule.onNodeWithTag(TestTags.SAVE_BUTTON).performClick()

        // is updated in list
        composeRule.onNodeWithText(noteTitle.plus("-new")).assertIsDisplayed()

    }

    @Test
    fun orderByTitleAscending() {
        // add items
        for (i in 'a'..'c') {
            composeRule.onNodeWithTag(TestTags.ADD_BUTTON).performClick()
            composeRule
                .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
                .performTextInput(i.toString())
            composeRule
                .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
                .performTextInput(i.toString())
            composeRule.onNodeWithTag(TestTags.SAVE_BUTTON).performClick()
        }

        // default order by
        composeRule.onNodeWithText("c").assertIsDisplayed()
        composeRule.onNodeWithText("b").assertIsDisplayed()
        composeRule.onNodeWithText("a").assertIsDisplayed()


        // order by title ascending
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule
            .onNodeWithContentDescription(context.resources.getString(R.string.sort_button_caption))
            .performClick()
        composeRule
            .onNodeWithText(context.resources.getString(R.string.title))
            .performClick()
        composeRule
            .onNodeWithText(context.resources.getString(R.string.ascending))
            .performClick()
        composeRule.onAllNodesWithTag(TestTags.LIST_ITEM)[0]
            .assertTextContains("a")
        composeRule.onAllNodesWithTag(TestTags.LIST_ITEM)[1]
            .assertTextContains("b")
        composeRule.onAllNodesWithTag(TestTags.LIST_ITEM)[2]
            .assertTextContains("c")
    }

    @Test
    fun deleteNote() {
        // add note
        val noteTitle = "test-title"
        val noteContent = "test-content"
        composeRule.onNodeWithTag(TestTags.ADD_BUTTON).performClick()
        composeRule
            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput(noteTitle)
        composeRule
            .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
            .performTextInput(noteContent)
        composeRule.onNodeWithTag(TestTags.SAVE_BUTTON).performClick()

        // delete note
        composeRule.onNodeWithTag(TestTags.DELETE_BUTTON).performClick()
        composeRule.onNodeWithText(noteTitle).assertIsNotDisplayed()

    }

}