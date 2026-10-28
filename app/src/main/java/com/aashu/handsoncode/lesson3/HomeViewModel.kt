package com.aashu.handsoncode.lesson3

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val api = FakeHomeApi()

    // Shown on screen: what was loaded and how long it took
    var result by mutableStateOf("Nothing loaded")
        private set

    var timeTaken by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun loadSequential() {
        // TODO(you) Step 1: load profile, orders and recommendations ONE AFTER ANOTHER.
        //   - put all three into `result`
        //   - measure the time it took and put it in `timeTaken`
        //     (hint: kotlin.system.measureTimeMillis { ... })
        //   - show/hide isLoading with try/finally
        //   Before running: how long do you expect it to take?
        isLoading = true
        viewModelScope.launch {
            try{
                val profile = api.getProfile()
                val orders = api.getOrders()
                val recommendations = api.getRecommendations()
                result = "$profile\n$orders\n$recommendations"
            } finally {
                isLoading = false
            }
        }
    }

    fun loadParallel() {
        // TODO(you) Step 2: same three calls, but make them run AT THE SAME TIME.
        //   - you need a coroutine builder that RETURNS a value (not launch)
        //   Before running: how long do you expect it to take now?
        isLoading=true
        viewModelScope.launch {
            try {
                val profileasyn= async { api.getProfile() }
                val ordersasyn= async { api.getOrders() }
                val recommendationsasyn= async { api.getRecommendations() }
                result = "${profileasyn.await()}\n${ordersasyn.await()}\n${recommendationsasyn.await()}"

            }finally {
                isLoading=false
            }
        }

    }

    fun loadDependent() {
        // TODO(you) Step 3: real apps often mix both:
        //   - first getUserId()           (needed by the next two calls)
        //   - then getOrders(userId) and getRecommendations(userId) at the same time
        //   Expected time? Write it down before you run it.
        isLoading=true
        viewModelScope.launch {
            try {
              val jobUserId=  async { api.getUserId() }
              val UserId= jobUserId.await()
              val jobOrders= async { api.getOrders(UserId) }
              val recommendations= async { api.getRecommendations(UserId) }
              result = "${jobOrders.await()}\n${recommendations.await()}"


            }finally {
                isLoading=false
            }
        }
    }
}
