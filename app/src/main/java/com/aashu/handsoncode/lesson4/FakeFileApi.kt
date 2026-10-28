package com.aashu.handsoncode.lesson4

import android.util.Log
import kotlinx.coroutines.delay

private const val TAG = "Lesson4"

// Given, don't change.
class FakeFileApi {

    // Downloads one chunk of a file: suspends ~500ms (like a network read).
    suspend fun downloadChunk(index: Int) {
        Log.d(TAG, "downloadChunk $index")
        delay(500)
    }

    // Encrypts one chunk: pure CPU work, ~300ms, NEVER suspends.
    fun encryptChunk(index: Int) {
        Log.d(TAG, "encryptChunk $index")
        val end = System.currentTimeMillis() + 300
        var x = 0L
        while (System.currentTimeMillis() < end) x = (x * 31 + 7) % 1_000_003
    }

    // A search that is SLOW for "phone" (3s) and FAST for "laptop" (1s).
    suspend fun search(query: String): String {
        Log.d(TAG, "search('$query'): start")
        delay(if (query == "phone") 3_000 else 1_000)
        Log.d(TAG, "search('$query'): end")
        return "Results for '$query'"
    }

    // Call this when a download is stopped halfway, to remove the broken file.
    fun deletePartialFile() {
        Log.d(TAG, "deletePartialFile: cleaned up")
    }
}
