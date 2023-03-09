package com.apprecipe.abngit.ui.list

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.apprecipe.abngit.data.ABNGitRepository
import com.apprecipe.abngit.data.model.Repo
import com.apprecipe.abngit.utils.NetworkConnectionMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ReposListViewModel @Inject constructor(
    private val repository: ABNGitRepository,
    val networkConnectionMonitor: NetworkConnectionMonitor
) : ViewModel() {

    fun getRepos(): Flow<PagingData<Repo>> = repository.getReposStream()
}
