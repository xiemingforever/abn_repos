package com.apprecipe.abngit.ui.list

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.apprecipe.abngit.data.ABNGitRepository
import com.apprecipe.abngit.data.database.RepoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ReposListViewModel @Inject constructor(
    private val repository: ABNGitRepository
) : ViewModel() {

//    private val _uiState: MutableStateFlow<ReposListUiState> =
//        MutableStateFlow(ReposListUiState.Loading)
//    val uiState: StateFlow<ReposListUiState> get() = _uiState

//    init {
//        viewModelScope.launch {
//            try {
//                _uiState.update { ReposListUiState.Success(repository.getABNGitRepos(1)) }
//            } catch (exception: Exception) {
//                _uiState.update { ReposListUiState.Error(exception) }
//            }
//        }
//    }

    fun getRepos(): Flow<PagingData<RepoEntity>> = repository.reposOfABN()
}

//sealed class ReposListUiState {
//    data class Success(val data: List<RepoEntity>) : ReposListUiState()
//    object Loading : ReposListUiState()
//    data class Error(val exception: Throwable) : ReposListUiState()
//}
