package com.apprecipe.abngit.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apprecipe.abngit.utils.ConnectionState
import com.apprecipe.abngit.utils.observeConnectivityAsFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun connectivityState(): State<ConnectionState> {
    val context = LocalContext.current
    return context.observeConnectivityAsFlow().collectAsState(initial = ConnectionState.Available)
}

@Composable
fun ConnectivityStatusBar() {
    Box(
        modifier = Modifier
            .background(Color.Red)
            .fillMaxWidth()
            .padding(8.dp), Alignment.Center
    ) {
        Text("No Internet Connection", color = Color.White, fontSize = 15.sp)
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
