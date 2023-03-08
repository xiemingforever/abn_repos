package com.apprecipe.abngit.ui.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.apprecipe.abngit.data.database.RepoEntity
import com.apprecipe.abngit.ui.list.RepoExtraInfo
import com.apprecipe.abngit.ui.list.RepoTitle
import com.apprecipe.abngit.ui.shared.ConnectivityStatusBar
import com.apprecipe.abngit.ui.shared.connectivityState
import com.apprecipe.abngit.utils.ConnectionState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun RepoDetailsScreen(
    modifier: Modifier = Modifier,
    repoId: Long,
    viewModel: RepoDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    viewModel.fetchRepo(repoId)
    val connection by connectivityState()
    val isConnected = connection == ConnectionState.Available

    Scaffold(
        content = { innerPadding ->
            Column {
                if (!isConnected) {
                    ConnectivityStatusBar()
                }
                when (uiState) {
                    is RepoDetailsUiState.Success -> {
                        RepoDetails(
                            modifier = modifier.padding(innerPadding),
                            repo = (uiState as RepoDetailsUiState.Success).repo,
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
fun RepoDetails(
    modifier: Modifier,
    repo: RepoEntity
) {
    Column(modifier = modifier.padding(16.dp)) {
        repo.avatarUrl?.let { HeaderImage(imageUrl = it) }
        Spacer(Modifier.height(16.dp))
        RepoTitle(title = repo.name)
        Spacer(Modifier.height(16.dp))
        RepoTitle(title = repo.fullName)
        Spacer(Modifier.height(16.dp))
        RepoExtraInfo(name = "Visibility", value = repo.visibility) // TODO
        Spacer(Modifier.height(16.dp))
        RepoExtraInfo(name = "Private repo", value = repo.isPrivate.toString()) // TODO
        Spacer(Modifier.height(16.dp))
        repo.description?.let { RepoDescription(description = it) }
    }
}

@Composable
private fun HeaderImage(imageUrl: String) {
    val imageModifier = Modifier
        .height(120.dp)
        .fillMaxWidth()
        .clip(shape = MaterialTheme.shapes.medium)
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = imageModifier,
        contentScale = ContentScale.FillHeight
    )
}

@Composable
fun RepoDescription(description: String) {
    Text(
        text = description,
        style = MaterialTheme.typography.bodyLarge
    )
}
