package com.wear.pin.presentation.pins

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material3.Card
import androidx.wear.compose.material3.CircularProgressIndicator
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import coil3.compose.SubcomposeAsyncImage

@Composable
fun PinsScreen(
    viewModel: PinsViewModel,
    boardId: String,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberScalingLazyListState()

    LaunchedEffect(boardId) {
        viewModel.loadPins(boardId)
    }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (val state = uiState) {
            is PinsUiState.Loading -> {
                CircularProgressIndicator()
            }
            is PinsUiState.Error -> {
                Text(
                    text = "Error: ${state.message}",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
            is PinsUiState.Success -> {
                if (state.pins.isEmpty()) {
                    Text(
                        text = "No pins found",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    ScalingLazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.pins) { pin ->
                            Card(
                                onClick = { /* TODO: Navigate to Pin Detail */ },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                if (pin.imageUrl != null) {
                                    SubcomposeAsyncImage(
                                        model = pin.imageUrl,
                                        contentDescription = pin.title,
                                        contentScale = ContentScale.Crop,
                                        modifier =
                                            Modifier
                                                .fillMaxWidth()
                                                .height(120.dp)
                                                .padding(bottom = 8.dp),
                                        loading = {
                                            Box(
                                                modifier = Modifier.fillMaxSize(),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                CircularProgressIndicator()
                                            }
                                        },
                                        error = {
                                            Box(
                                                modifier =
                                                    Modifier
                                                        .fillMaxSize()
                                                        .background(Color.DarkGray),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text("Error", color = Color.White)
                                            }
                                        }
                                    )
                                }

                                Text(
                                    text = pin.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                if (!pin.description.isNullOrEmpty()) {
                                    Text(
                                        text = pin.description,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
