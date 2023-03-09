package com.apprecipe.abngit.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.apprecipe.abngit.data.model.Repo
import com.apprecipe.abngit.ui.shared.ConnectivityStatusBar

@Composable
fun ReposListScreen(
    modifier: Modifier = Modifier,
    onListItemClick: (Repo) -> Unit,
    viewModel: ReposListViewModel = hiltViewModel()
) {
    val repos = viewModel.getRepos().collectAsLazyPagingItems()
//    val uiState by viewModel.uiState.collectAsState()
    val isOnline by viewModel.networkConnectionMonitor.isConnected.collectAsState(true)

    Scaffold(
        content = { innerPadding ->
            Column {
                if (!isOnline) {
                    ConnectivityStatusBar()
                }

                when (repos.loadState.refresh) {
                    is LoadState.Error ->
                        LoadingStatusBar("Error: not able to load initial data from backend")
                    is LoadState.Loading ->
                        LoadingStatusBar("Loading data from backend")
                    else -> {}
                }

                when (repos.loadState.append) {
                    is LoadState.Error ->
                        LoadingStatusBar("Error: not able to load data from backend")
                    is LoadState.Loading ->
                        LoadingStatusBar("Loading more data from backend")
                    else -> {}
                }
//                when (uiState) {
//                    is ReposListUiState.Success -> {
                ReposList(
                    modifier = modifier.padding(innerPadding),
                    lazyPagingItems = repos,
                    onListItemClick = onListItemClick
                )
//                    }
//                    else -> {
//
//                    }
//                }
            }
        }
    )
}

@Composable
fun ReposList(
    modifier: Modifier,
    lazyPagingItems: LazyPagingItems<Repo>,
    onListItemClick: (Repo) -> Unit
) {
    LazyColumn(modifier = modifier) {

        items(lazyPagingItems) { item ->
            item?.let { RepoCard(repo = it, onListItemClick) }
        }

        if (lazyPagingItems.loadState.append == LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun LoadingStatusBar(text: String) {
    Box(
        modifier = Modifier
            .background(Color.Yellow)
            .fillMaxWidth()
            .padding(8.dp), Alignment.Center
    ) {
        Text(text = text, color = Color.Black, fontSize = 15.sp)
    }
}
