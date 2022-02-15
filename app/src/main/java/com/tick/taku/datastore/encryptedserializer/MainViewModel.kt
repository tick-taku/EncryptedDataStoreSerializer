package com.tick.taku.datastore.encryptedserializer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tick.taku.datastore.encryptedserializer.datasource.TextPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStore: TextPreferencesDataStore
): ViewModel() {

    data class UiState(
        val text: String = ""
    )

    val uiState: StateFlow<UiState> = dataStore.load().map {
        UiState(text = it)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UiState())

    fun save(text: String) {
        viewModelScope.launch {
            dataStore.save(text)
        }
    }
}