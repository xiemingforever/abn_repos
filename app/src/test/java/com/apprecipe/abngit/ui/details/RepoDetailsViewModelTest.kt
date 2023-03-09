package com.apprecipe.abngit.ui.details

import com.apprecipe.abngit.data.ABNGitRepository
import com.apprecipe.abngit.utils.NetworkConnectionMonitor
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class RepoDetailsViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: RepoDetailsViewModel

    private val repository = mockk<ABNGitRepository>(relaxed = true)

    private val networkConnectionMonitor = mockk<NetworkConnectionMonitor>(relaxed = true)

    @Before
    fun setUp() {
        viewModel = RepoDetailsViewModel(repository, networkConnectionMonitor, testDispatcher)
    }

    @Test
    fun testFetchRepo() {
        runTest {
            val repoId = 112761951L
            viewModel.fetchRepo(repoId)

            coVerify { repository.getRepo(repoId) }
        }
    }
}
