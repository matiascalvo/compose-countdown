package com.matias.countdown

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.matias.countdown.ui.main.MainActivity
import com.matias.countdown.ui.main.MainScreen
import com.matias.countdown.ui.main.MainViewModel
import com.matias.countdown.ui.theme.CountDownTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalAnimationApi
@ExperimentalFoundationApi
class MainTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun before() {
        composeTestRule.setContent {
            CountDownTheme {
                MainScreen(MainViewModel())
            }
        }
    }

    @Test
    fun whenScreenIsEmptyThenBottomActionButtonIsHidden() {
        composeTestRule.onNodeWithTag("BottomActionButton").assertDoesNotExist()
    }

    @Test
    fun givenScreenJustStartedThenCountdownTextIsDisplayed() {
        val text = composeTestRule.activity.getString(R.string.countdown)
        composeTestRule.onNodeWithText(text).assertExists()
    }

    @Test
    fun whenClicksOn12345Then12345IsDisplayed() {
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.onNodeWithText("4").performClick()
        composeTestRule.onNodeWithText("5").performClick()

        composeTestRule.onNodeWithText("01").assertIsDisplayed()
        composeTestRule.onNodeWithText("23").assertIsDisplayed()
        composeTestRule.onNodeWithText("45").assertIsDisplayed()
    }

    @Test
    fun whenClicksOn12AndBackThen12345IsDisplayed() {
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("2").performClick()

        composeTestRule.onNodeWithTag("BackSpace").performClick()

        composeTestRule.onNodeWithText("01").assertIsDisplayed()
    }
}
