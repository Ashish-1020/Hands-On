package com.aashu.handsoncode.lesson4

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class FileViewModel : ViewModel() {

    private val api = FakeFileApi()
    private val chunks = 10

    // ---------- Step 1: download with a Cancel button ----------
    var downloadProgress by mutableFloatStateOf(0f)   // 0f..1f
        private set
    var downloadStatus by mutableStateOf("Idle")
        private set

    private var downloadJob: Job? = null

    fun startDownload() {
        // TODO(you) Step 1:
        //   - download chunks 0 until `chunks` with api.downloadChunk(i)
        //   - update downloadProgress after each chunk, status "Downloading" / "Done"
        //   - keep a reference so cancelDownload() can stop it
        //   - if it is stopped halfway: status "Cancelled" AND call api.deletePartialFile()
        //     (hint: what runs even when a coroutine is cancelled?)
        //   - tapping Start twice must not run two downloads at once
        if(downloadStatus=="Downloading") return
        downloadStatus="Downloading"
        downloadJob=viewModelScope.launch {
            try {

                    for (i in 0 until chunks) {
                        api.downloadChunk(i)
                        downloadProgress = (i + 1) / chunks.toFloat()
                    }
                    downloadStatus = "Done"

            }catch (e: Exception) {
                downloadStatus = "Cancelled"
                api.deletePartialFile()
            }
        }
    }

    fun cancelDownload() {
        // TODO(you) Step 1: stop the running download
        downloadJob?.cancel()
    }

    // ---------- Step 2: CPU work that ignores Cancel ----------
    var encryptProgress by mutableFloatStateOf(0f)
        private set
    var encryptStatus by mutableStateOf("Idle")
        private set

    fun startEncrypt() {
        // TODO(you) Step 2:
        //   - encrypt chunks 0 until `chunks` with api.encryptChunk(i), off the main thread
        //   - update encryptProgress and encryptStatus like Step 1
        //   - FIRST write it the obvious way and press Cancel halfway. Does it stop?
        //   - THEN fix it so Cancel stops it after the current chunk
    }

    fun cancelEncrypt() {
        // TODO(you) Step 2
    }

    // ---------- Step 3: only the latest search may win ----------
    var searchResult by mutableStateOf("No search yet")
        private set

    private var searchjob: Job?=null

    fun search(query: String) {
        // TODO(you) Step 3:
        //   - FIRST write it the obvious way: launch, call api.search(query), show the result.
        //     Tap "phone" then quickly "laptop". What is on screen at the end? Is it right?
        //   - THEN fix it so a new search cancels the old one
        searchjob?.cancel()
         searchjob=viewModelScope.launch {
            searchResult=api.search(query)
        }
    }
}
