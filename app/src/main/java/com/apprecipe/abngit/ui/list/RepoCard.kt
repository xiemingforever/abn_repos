package com.apprecipe.abngit.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.apprecipe.abngit.data.database.RepoEntity

@Composable
fun RepoImage(imageUrl: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = modifier
            .size(40.dp, 40.dp)
            .clip(MaterialTheme.shapes.small)
    )
}

@Composable
fun RepoTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.h5,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun RepoExtraInfo(name: String, value: String) {
    Text(
        text = "$name - $value",
        style = MaterialTheme.typography.body1
    )
}

@Composable
fun RepoCard(
    repo: RepoEntity,
    navigateToDetails: (RepoEntity) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = { navigateToDetails })
    ) {
        if (repo.avatarUrl != null) {
            RepoImage(imageUrl = repo.avatarUrl)
        }
        Column(modifier = Modifier.padding(vertical = 10.dp)) {
            RepoTitle(title = repo.name)
            RepoExtraInfo(name = "Visibility", value = repo.visibility.toString()) // TODO
            RepoExtraInfo(name = "Private repo", value = repo.isPrivate.toString()) // TODO
        }
    }
}
