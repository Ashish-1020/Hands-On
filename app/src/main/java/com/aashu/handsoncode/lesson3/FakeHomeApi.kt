package com.aashu.handsoncode.lesson3

import android.util.Log
import kotlinx.coroutines.delay

private const val TAG = "Lesson3"

// The home screen of a shopping app needs data from several endpoints.
// All functions are given (they suspend, so they are already main-safe). Don't change them.
class FakeHomeApi {

    suspend fun getProfile(): String {
        Log.d(TAG, "getProfile: start")
        delay(2_000)
        Log.d(TAG, "getProfile: end")
        return "Aashu"
    }

    suspend fun getOrders(userId: String = "me"): String {
        Log.d(TAG, "getOrders($userId): start")
        delay(3_000)
        Log.d(TAG, "getOrders($userId): end")
        return "3 orders"
    }

    suspend fun getRecommendations(userId: String = "me"): String {
        Log.d(TAG, "getRecommendations($userId): start")
        delay(1_000)
        Log.d(TAG, "getRecommendations($userId): end")
        return "Pixel 9, AirPods"
    }

    // Step 3 only: orders and recommendations NEED this id first.
    suspend fun getUserId(): String {
        Log.d(TAG, "getUserId: start")
        delay(1_000)
        Log.d(TAG, "getUserId: end")
        return "user-42"
    }
}
