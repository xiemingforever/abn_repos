package com.apprecipe.abngit.list

import androidx.compose.material.Surface
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.apprecipe.abngit.R
import com.apprecipe.abngit.getTestRepo
import com.apprecipe.abngit.ui.list.RepoCard
import com.apprecipe.abngit.ui.theme.ABNGitTheme
import org.junit.Rule
import org.junit.Test

class RepoCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testRepoCard() {
        val repo = getTestRepo()

        composeTestRule.setContent {
            ABNGitTheme {
                Surface {
                    RepoCard(repo = repo, navigateToDetails = {})
                }
            }
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        composeTestRule.onNodeWithText(repo.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(repo.fullName).assertDoesNotExist()
        composeTestRule.onNodeWithText(repo.description!!).assertDoesNotExist()
        composeTestRule.onNodeWithText(
            context.getString(R.string.visibility) + " - " + repo.visibility
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            context.getString(R.string.private_repo) + " - " + repo.isPrivate
        ).assertIsDisplayed()
    }
}
