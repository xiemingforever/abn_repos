package com.apprecipe.abngit.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apprecipe.abngit.R

@Composable
fun RepoExtraInfo(name: String, value: String) {
    Text(
        text = "$name - $value",
        style = MaterialTheme.typography.body1
    )
}

@Composable
fun WarningStatusBar(text: String) {
    Box(
        modifier = Modifier
            .background(Color.Yellow)
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color.Black, fontSize = 15.sp)
    }
}

@Composable
fun ConnectivityStatusBar() {
    Box(
        modifier = Modifier
            .background(Color.Red)
            .fillMaxWidth()
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(stringResource(R.string.no_internet), color = Color.White, fontSize = 14.sp)
    }
}
