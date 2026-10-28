package com.aashu.handsoncode.lesson4

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Lesson4Screen(
    modifier: Modifier = Modifier,
    viewModel: FileViewModel = viewModel()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Lesson 4: Stopping work", style = MaterialTheme.typography.headlineSmall)

        Text("Step 1: Download (suspending)", style = MaterialTheme.typography.titleMedium)
        LinearProgressIndicator(progress = { viewModel.downloadProgress }, modifier = Modifier.fillMaxWidth())
        Text(viewModel.downloadStatus)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = viewModel::startDownload) { Text("Start") }
            OutlinedButton(onClick = viewModel::cancelDownload) { Text("Cancel") }
        }

        HorizontalDivider()

        Text("Step 2: Encrypt (CPU, never suspends)", style = MaterialTheme.typography.titleMedium)
        LinearProgressIndicator(progress = { viewModel.encryptProgress }, modifier = Modifier.fillMaxWidth())
        Text(viewModel.encryptStatus)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = viewModel::startEncrypt) { Text("Start") }
            OutlinedButton(onClick = viewModel::cancelEncrypt) { Text("Cancel") }
        }

        HorizontalDivider()

        Text("Step 3: Search (phone = 3s, laptop = 1s)", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { viewModel.search("phone") }) { Text("Search phone") }
            Button(onClick = { viewModel.search("laptop") }) { Text("Search laptop") }
        }
        Text(viewModel.searchResult, style = MaterialTheme.typography.titleLarge)
    }
}
