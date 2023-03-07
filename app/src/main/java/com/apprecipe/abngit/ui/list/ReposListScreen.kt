package com.apprecipe.abngit.ui.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.apprecipe.abngit.data.database.RepoEntity
import com.apprecipe.abngit.ui.shared.ConnectivityStatusBar
import com.apprecipe.abngit.ui.shared.connectivityState
import com.apprecipe.abngit.ui.theme.ReposListUiState
import com.apprecipe.abngit.ui.theme.ReposListViewModel
import com.apprecipe.abngit.utils.ConnectionState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun ReposListScreen(
    modifier: Modifier = Modifier,
    onListItemClick: (RepoEntity) -> Unit,
    viewModel: ReposListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val connection by connectivityState()
    val isConnected = connection == ConnectionState.Available

    Scaffold(
        content = { innerPadding ->
            Column {
                if (!isConnected) {
                    ConnectivityStatusBar()
                }
                when (uiState) {
                    is ReposListUiState.Success -> {
                        ReposList(
                            modifier = modifier.padding(innerPadding),
                            repos = (uiState as ReposListUiState.Success).data,
                            onListItemClick = onListItemClick
                        )
                    }
                    else -> {

                    }
                }
            }
        }
    )
}

@Composable
fun ReposList(
    modifier: Modifier,
    repos: List<RepoEntity>,
    onListItemClick: (RepoEntity) -> Unit,
) {
    LazyColumn {
        items(repos.size) { index ->
            RepoCard(repo = repos[index], onListItemClick)
        }
    }
}
