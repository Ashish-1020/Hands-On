package com.aashu.handsoncode.lesson2

import android.util.Log
import kotlinx.coroutines.delay

private const val TAG = "Lesson2"

// Two kinds of slow work you cannot make "suspend" yourself. Both are given, don't change them.
class ReportTools {

    // CPU work: keeps the processor busy for ~3 seconds (like sorting a huge list,
    // parsing big JSON, resizing an image). No waiting, pure calculation.
    fun calculateReport(): String {
        Log.d(TAG, "calculateReport: start on ${Thread.currentThread().name}")
        val end = System.currentTimeMillis() + 3_000
        var checksum = 0L
        while (System.currentTimeMillis() < end) {
            checksum = (checksum * 31 + 7) % 1_000_003
        }
        Log.d(TAG, "calculateReport: end on ${Thread.currentThread().name}")
        return "Report ready (checksum $checksum)"
    }

    // Blocking I/O: an old SDK / file / database call that just WAITS for ~3 seconds.
    // It has no suspend version, so it blocks whatever thread calls it.
     fun readLegacyDatabase(): String {
        Log.d(TAG, "readLegacyDatabase: start on ${Thread.currentThread().name}")
        Thread.sleep(3_000)
        Log.d(TAG, "readLegacyDatabase: end on ${Thread.currentThread().name}")
        return "42 orders loaded"
    }
}
