package com.apprecipe.abngit.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ConnectivityStatusBar() {
    Box(
        modifier = Modifier
            .background(Color.Red)
            .fillMaxWidth()
            .padding(4.dp), Alignment.Center
    ) {
        Text("No Internet", color = Color.White, fontSize = 14.sp)
    }
}

@Composable
fun CenteredLoadingSpinner() {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}
