package com.aashu.handsoncode.lesson3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Lesson3Screen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Lesson 3: Doing things at the same time", style = MaterialTheme.typography.headlineSmall)
        Text("profile = 2s, orders = 3s, recommendations = 1s, userId = 1s")

        Button(onClick = viewModel::loadSequential, enabled = !viewModel.isLoading) {
            Text("Step 1: One after another")
        }
        Button(onClick = viewModel::loadParallel, enabled = !viewModel.isLoading) {
            Text("Step 2: All at the same time")
        }
        Button(onClick = viewModel::loadDependent, enabled = !viewModel.isLoading) {
            Text("Step 3: userId first, then the rest")
        }

        if (viewModel.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        HorizontalDivider()

        Text(viewModel.result)
        Text(viewModel.timeTaken, style = MaterialTheme.typography.titleLarge)
    }
}
