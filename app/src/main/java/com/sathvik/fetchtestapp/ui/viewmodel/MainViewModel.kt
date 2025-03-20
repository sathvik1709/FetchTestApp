package com.sathvik.fetchtestapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sathvik.fetchtestapp.netowork.repository.MainRepository
import com.sathvik.fetchtestapp.netowork.repository.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<MainUIState> = MutableStateFlow(MainUIState.Init)
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MainUIState.Init
    )

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            _uiState.update {
                MainUIState.Loading
            }
            when (val response = repository.getData()) {
                is Result.Error -> {
                    _uiState.update {
                        MainUIState.Error(response.e.localizedMessage ?: "Something went wrong!1")
                    }
                }
                is Result.Success<List<ListContent>> -> {
                    _uiState.update {
                        MainUIState.Data(response.data)
                    }
                }
            }
        }
    }


}