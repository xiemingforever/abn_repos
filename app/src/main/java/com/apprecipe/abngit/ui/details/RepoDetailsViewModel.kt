package com.apprecipe.abngit.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apprecipe.abngit.data.ABNGitRepository
import com.apprecipe.abngit.data.database.RepoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoDetailsViewModel @Inject constructor(
    private val repository: ABNGitRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<RepoDetailsUiState> =
        MutableStateFlow(RepoDetailsUiState.Loading)
    val uiState: StateFlow<RepoDetailsUiState> get() = _uiState

    fun fetchRepo(id: Int) {
        viewModelScope.launch {
            try {
                _uiState.update { RepoDetailsUiState.Success(repository.getRepo(id)) }
            } catch (exception: Exception) {
                _uiState.update { RepoDetailsUiState.Error(exception) }
            }
        }
    }
}

sealed class RepoDetailsUiState {
    data class Success(val repo: RepoEntity) : RepoDetailsUiState()
    object Loading : RepoDetailsUiState()
    data class Error(val exception: Throwable) : RepoDetailsUiState()
}