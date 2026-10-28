package com.aashu.handsoncode.lesson2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher


class ReportViewModel : ViewModel() {

    private val tools = ReportTools()

    var report by mutableStateOf("Not generated")
        private set

    var orders by mutableStateOf("Not loaded")
        private set

    var isWorking by mutableStateOf(false)
        private set

    fun generateReport() {
        // TODO(you) Step 1: do it exactly like Lesson 1:
        //   viewModelScope.launch { report = tools.calculateReport() }
        //   Run it. Does the UI freeze? (It's inside a coroutine now...)
        //
        // TODO(you) Step 2: fix it so the UI never freezes.
        //   - the heavy part must run on a background thread
        //   - `report` must still be updated on the main thread
        //   - pick the dispatcher that fits CPU work
        //   - show/hide `isWorking` like in Lesson 1

        isWorking = true
        viewModelScope.launch {
            try {
                report = withContext(Dispatchers.Default) { tools.calculateReport() }
            } finally {
                isWorking = false
            }

        }
    }

    fun loadOrders() {
        // TODO(you) Step 3: same idea for tools.readLegacyDatabase()
        //   - pick the dispatcher that fits blocking I/O (it is NOT the same one)
        isWorking=true
        viewModelScope.launch {
            try {
                orders = withContext(Dispatchers.IO) { tools.readLegacyDatabase() }
            } finally {
                isWorking = false
            }
        }
    }
}
