package com.aashu.handsoncode.lesson1

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// Lesson 1 keeps state in simple Compose state. StateFlow comes in a later lesson.
class ProfileViewModel : ViewModel() {

    private val api = FakeProfileApi()

    var profile by mutableStateOf("Not loaded")
        private set

    var isLoading by mutableStateOf(false)
        private set

    // Normal (not suspend) function: the button's onClick can call it directly
    fun loadProfile() {
        isLoading = true
        // launch = the bridge from normal code into a coroutine.
        // viewModelScope cancels it automatically when the ViewModel is cleared.
        viewModelScope.launch {
            try {
                profile = api.loadProfile()   // suspends here, main thread stays free
            } finally {
                isLoading = false             // runs even if loadProfile() throws
            }
        }
    }
}
