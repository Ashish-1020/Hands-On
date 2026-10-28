package com.aashu.handsoncode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aashu.handsoncode.lesson1.FakeProfileApi
import com.aashu.handsoncode.lesson1.ProfileViewModel
import com.aashu.handsoncode.lesson2.Lesson2Screen
import com.aashu.handsoncode.lesson3.Lesson3Screen
import com.aashu.handsoncode.lesson4.Lesson4Screen
import com.aashu.handsoncode.ui.theme.HandsonCodeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HandsonCodeTheme {
                // Lesson picker: the newest lesson opens by default
                var lesson by rememberSaveable { mutableIntStateOf(4) }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        PrimaryTabRow(
                            selectedTabIndex = lesson - 1,
                            modifier = Modifier.statusBarsPadding()
                        ) {
                            listOf(1, 2, 3, 4).forEach { number ->
                                Tab(
                                    selected = lesson == number,
                                    onClick = { lesson = number },
                                    text = { Text("Lesson $number") }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    when (lesson) {
                        1 -> Lesson1Screen(modifier = Modifier.padding(innerPadding))
                        2 -> Lesson2Screen(modifier = Modifier.padding(innerPadding))
                        3 -> Lesson3Screen(modifier = Modifier.padding(innerPadding))
                        4 -> Lesson4Screen(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@Composable
fun Lesson1Screen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel()
) {
    val api = remember { FakeProfileApi() }
    var blockingResult by remember { mutableStateOf("Not loaded") }
    var taps by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Lesson 1: Why coroutines?", style = MaterialTheme.typography.headlineSmall)

        // Always spinning. If it STOPS, the main thread is frozen.
        CircularProgressIndicator()

        // Tap this while a load is running. If the number doesn't change, the UI is frozen.
        OutlinedButton(onClick = { taps++ }) {
            Text("Am I responsive? Taps: $taps")
        }

        HorizontalDivider()

        Text("Step 1: the blocking way")
        Button(onClick = { blockingResult = api.loadProfileBlocking() }) {
            Text("Load profile (blocking)")
        }
        Text(blockingResult)

        HorizontalDivider()

        Text("Step 2 + 3: your coroutine")
        Button(onClick = viewModel::loadProfile, enabled = !viewModel.isLoading) {
            Text(if (viewModel.isLoading) "Loading..." else "Load profile (coroutine)")
        }
        Text(viewModel.profile)
    }
}

