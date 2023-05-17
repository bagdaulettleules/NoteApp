package com.example.noteapp.feature_main.presentation.list

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import com.example.noteapp.R
import com.example.noteapp.core.util.TestTags
import com.example.noteapp.di.AppModule
import com.example.noteapp.feature_main.presentation.MainActivity
import com.example.noteapp.feature_main.presentation.util.Screen
import com.example.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesListScreenKtTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            NoteAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesList.route
                ) {
                    composable(Screen.NotesList.route) {
                        val viewModel = hiltViewModel<NotesListViewModel>()
                        NotesListScreen(
                            navController = navController,
                            viewState = viewModel.state.value,
                            onEvent = viewModel::onEvent
                        )
                    }
                }
            }
        }
    }

    @Test
    fun orderBySection() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule
            .onNodeWithContentDescription(context.resources.getString(R.string.sort_button_caption))
            .performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()

        composeRule
            .onNodeWithText(context.resources.getString(R.string.title))
            .performClick()
        composeRule
            .onNodeWithText(context.resources.getString(R.string.title))
            .assertIsNotEnabled()
    }

    @Test
    fun addButtonIsVisible() {
        composeRule.onNodeWithTag(TestTags.ADD_BUTTON).assertIsDisplayed()
    }
}