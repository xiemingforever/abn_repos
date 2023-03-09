package com.apprecipe.abngit.details

import androidx.compose.material.Surface
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.apprecipe.abngit.R
import com.apprecipe.abngit.getTestRepo
import com.apprecipe.abngit.ui.details.RepoDetails
import com.apprecipe.abngit.ui.theme.ABNGitTheme
import org.junit.Rule
import org.junit.Test

class RepoDetailsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testRepoDetails() {
        val repo = getTestRepo()

        composeTestRule.setContent {
            ABNGitTheme {
                Surface {
                    RepoDetails(repo = repo)
                }
            }
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        composeTestRule.onNodeWithText(repo.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(repo.fullName).assertIsDisplayed()
        composeTestRule.onNodeWithText(repo.description!!).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            context.getString(R.string.visibility) + " - " + repo.visibility
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            context.getString(R.string.private_repo) + " - " + repo.isPrivate
        ).assertIsDisplayed()
    }
}
