package com.aashu.handsoncode.lesson2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Lesson2Screen(
    modifier: Modifier = Modifier,
    viewModel: ReportViewModel = viewModel()
) {
    var taps by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Lesson 2: Which thread?", style = MaterialTheme.typography.headlineSmall)

        // Same freeze detectors as Lesson 1
        CircularProgressIndicator()
        OutlinedButton(onClick = { taps++ }) {
            Text("Am I responsive? Taps: $taps")
        }

        HorizontalDivider()

        Text("CPU work")
        Button(onClick = viewModel::generateReport, enabled = !viewModel.isWorking) {
            Text("Generate report")
        }
        Text(viewModel.report)

        HorizontalDivider()

        Text("Blocking I/O")
        Button(onClick = viewModel::loadOrders, enabled = !viewModel.isWorking) {
            Text("Load orders")
        }
        Text(viewModel.orders)
    }
}
