package com.apprecipe.abngit.ui.list

import com.apprecipe.abngit.data.ABNGitRepository
import com.apprecipe.abngit.utils.NetworkConnectionMonitor
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class ReposListViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: ReposListViewModel

    private val repository = mockk<ABNGitRepository>(relaxed = true)

    private val networkConnectionMonitor = mockk<NetworkConnectionMonitor>(relaxed = true)

    @Before
    fun setUp() {
        viewModel = ReposListViewModel(repository, networkConnectionMonitor, testDispatcher)
    }

    @Test
    fun testGetRepos() {
        runTest {
            viewModel.getRepos()

            coVerify { repository.getReposStream() }
        }
    }
}
