package com.apprecipe.abngit.ui.list

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.apprecipe.abngit.R
import com.apprecipe.abngit.data.model.Repo
import com.apprecipe.abngit.ui.shared.RepoExtraInfo
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
fun RepoCard(
    repo: Repo,
    onRepoCardClick: (Repo) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onRepoCardClick(repo) })
    ) {
        if (repo.avatarUrl != null) {
            RepoImage(imageUrl = repo.avatarUrl, modifier = Modifier.padding(16.dp))
        }
        Column(modifier = Modifier.padding(vertical = 10.dp)) {
            RepoTitle(title = repo.name)
            Spacer(Modifier.height(8.dp))
            RepoExtraInfo(name = stringResource(R.string.visibility), value = repo.visibility)
            Spacer(Modifier.height(8.dp))
            RepoExtraInfo(
                name = stringResource(R.string.private_repo),
                value = repo.isPrivate.toString()
            )
        }
    }
}

@Preview("Repo card")
@Preview("Repo card (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun RepoCardPreview() {
    ABNGitTheme {
        Surface {
            RepoCard(
                Repo(
                    generatedId = 1,
                    repoId = 112761951,
                    name = "ToggleProc",
                    fullName = "abnamrocoesd/ToggleProc",
                    description = "Make your toggles manageable with this library.",
                    avatarUrl = "https://avatars.githubusercontent.com/u/15876397?v=4",
                    visibility = "public",
                    isPrivate = false,
                    htmlUrl = "https://github.com/abnamrocoesd/ToggleProc",
                )
            ) {}
        }
    }
}
