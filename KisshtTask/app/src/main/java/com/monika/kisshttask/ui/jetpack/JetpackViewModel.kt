package com.monika.kisshttask.ui.jetpack

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.monika.kisshttask.model.Poster
import com.monika.kisshttask.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class JetpackViewModel @Inject constructor(
    mainRepository: MainRepository
) : ViewModel() {

    val posterList: Flow<List<Poster>> =
        mainRepository.loadPosters(
            onStart = { _isLoading.value = true },
            onCompletion = { _isLoading.value = false },
            onError = { Log.d("MainViewModel", it) }
        )

    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _selectedTab: MutableState<Int> = mutableStateOf(0)
    val selectedTab: State<Int> get() = _selectedTab

    init {
        Log.d("JetpackViewModel", "injection MainViewModel")
    }

    fun selectTab(@StringRes tab: Int) {
        _selectedTab.value = tab
    }
}
