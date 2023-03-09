package com.apprecipe.abngit.ui.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.apprecipe.abngit.data.model.Repo
import com.apprecipe.abngit.ui.list.RepoTitle
import com.apprecipe.abngit.ui.shared.ConnectivityStatusBar
import com.apprecipe.abngit.ui.shared.RepoExtraInfo

@Composable
fun RepoDetailsScreen(
    modifier: Modifier = Modifier,
    repoId: Long,
    viewModel: RepoDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.fetchRepo(repoId)
    val isOnline by viewModel.networkConnectionMonitor.isConnected.collectAsStateWithLifecycle(true)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Repo Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, "Back icon")
                    }
                },
            )
        },
        content = { innerPadding ->
            Column {
                if (!isOnline) {
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
    repo: Repo
) {
    Column(modifier = modifier.padding(16.dp)) {
        repo.avatarUrl?.let { HeaderImage(imageUrl = it) }
        Spacer(Modifier.height(16.dp))
        RepoTitle(title = repo.name)
        Spacer(Modifier.height(16.dp))
        BodyText(text = repo.fullName)
        Spacer(Modifier.height(16.dp))
        RepoExtraInfo(name = "Visibility", value = repo.visibility) // TODO
        Spacer(Modifier.height(16.dp))
        RepoExtraInfo(name = "Private repo", value = repo.isPrivate.toString()) // TODO
        Spacer(Modifier.height(16.dp))
        repo.description?.let { BodyText(text = it) }
        Spacer(Modifier.height(16.dp))
        LinkButton(
            btnText = "Open in browser",
            url = repo.htmlUrl
        )
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
fun BodyText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.body1
    )
}

@Composable
fun LinkButton(btnText: String, url: String) {
    val uriHandler = LocalUriHandler.current
    OutlinedButton(onClick = { uriHandler.openUri(url) }) {
        Text(btnText)
    }
}
