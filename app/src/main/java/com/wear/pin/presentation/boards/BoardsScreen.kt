package com.wear.pin.presentation.boards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun BoardsScreen(
    viewModel: BoardsViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberScalingLazyListState()

    LaunchedEffect(Unit) {
        viewModel.loadBoards()
    }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (val state = uiState) {
            is BoardsUiState.Loading -> {
                CircularProgressIndicator()
            }
            is BoardsUiState.Error -> {
                Text(
                    text = "Error: ${state.message}",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
            is BoardsUiState.Success -> {
                if (state.boards.isEmpty()) {
                    Text(
                        text = "No boards found",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    ScalingLazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.boards) { board ->
                            Card(
                                onClick = { /* TODO: Navigate to Pins */ },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = board.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                if (!board.description.isNullOrEmpty()) {
                                    Text(
                                        text = board.description,
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
