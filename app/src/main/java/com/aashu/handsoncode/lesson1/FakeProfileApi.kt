package com.aashu.handsoncode.lesson1

import android.util.Log
import kotlinx.coroutines.delay

private const val TAG = "Lesson1"

// Pretends to be a slow server: every request takes 3 seconds.
class FakeProfileApi {

    // The old way: blocks whatever thread calls it for 3 seconds.
    fun loadProfileBlocking(): String {
        Log.d(TAG, "loadProfileBlocking: start on ${Thread.currentThread().name}")
        Thread.sleep(3_000)
        Log.d(TAG, "loadProfileBlocking: end on ${Thread.currentThread().name}")
        return "Aashu - Android Developer"
    }


    // The coroutine way: waits 3 seconds WITHOUT blocking the thread.
    suspend fun loadProfile(): String {
        Log.d(TAG, "loadProfile: start on ${Thread.currentThread().name}")
        delay(3_000)
        Log.d(TAG, "loadProfile: end on ${Thread.currentThread().name}")
        return "Aashu - Android Developer"
    }

}
