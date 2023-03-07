package com.apprecipe.abngit.ui.list

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.apprecipe.abngit.data.database.RepoEntity
import com.apprecipe.abngit.ui.theme.ABNGitTheme

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
            .fillMaxWidth()
            .clickable(onClick = { navigateToDetails(repo) })
    ) {
        if (repo.avatarUrl != null) {
            RepoImage(imageUrl = repo.avatarUrl, modifier = Modifier.padding(16.dp))
        }
        Column(modifier = Modifier.padding(vertical = 10.dp)) {
            RepoTitle(title = repo.name)
            RepoExtraInfo(name = "Visibility", value = repo.visibility) // TODO
            RepoExtraInfo(name = "Private repo", value = repo.isPrivate.toString()) // TODO
        }
    }
}

@Preview("Repo card")
@Preview("Repo card (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SimplePostPreview() {
    ABNGitTheme {
        Surface {
            RepoCard(
                RepoEntity(
                    id = 132,
                    name = "fadsfa",
                    fullName = "fdafasf",
                    description = "ffdasfs",
                    avatarUrl = "fda",
                    visibility = "fda",
                    isPrivate = false,
                    htmlUrl = "daf",
                    page = 2L
                ), {})
        }
    }
}
