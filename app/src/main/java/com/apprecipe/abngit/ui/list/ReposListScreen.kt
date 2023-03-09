package com.apprecipe.abngit.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.apprecipe.abngit.data.model.Repo
import com.apprecipe.abngit.ui.shared.ConnectivityStatusBar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReposListScreen(
    modifier: Modifier = Modifier,
    onListItemClick: (Repo) -> Unit,
    viewModel: ReposListViewModel = hiltViewModel()
) {
    val repos = viewModel.getRepos().collectAsLazyPagingItems()
    val isOnline by viewModel.networkConnectionMonitor.isConnected.collectAsStateWithLifecycle(true)
    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(isRefreshing, { repos.refresh() })

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "ABN AMRO Repos") })
        },
        content = { innerPadding ->
            Box(
                modifier
                    .padding(innerPadding)
                    .pullRefresh(pullRefreshState)
            ) {
                Column {
                    if (!isOnline) {
                        ConnectivityStatusBar()
                    }

                    when (repos.loadState.refresh) {
                        is LoadState.Error -> {
                            isRefreshing = false
                            if (isOnline) {
                                LoadingStatusBar("Error: not able to load initial data from backend")
                            }
                        }
                        is LoadState.Loading ->
                            LinearProgressIndicator(modifier.fillMaxWidth())
                        else -> {
                            isRefreshing = false
                        }
                    }

                    when (repos.loadState.append) {
                        is LoadState.Error -> {
                            isRefreshing = false
                            if (isOnline) {
                                LoadingStatusBar("Error: not able to load data from backend")
                            }
                        }
                        is LoadState.Loading ->
                            LinearProgressIndicator(modifier.fillMaxWidth())
                        else -> {
                            isRefreshing = false
                        }
                    }

                    ReposList(
                        modifier = modifier,
                        lazyPagingItems = repos,
                        onListItemClick = onListItemClick
                    )
                }
                PullRefreshIndicator(
                    refreshing = isRefreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
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
