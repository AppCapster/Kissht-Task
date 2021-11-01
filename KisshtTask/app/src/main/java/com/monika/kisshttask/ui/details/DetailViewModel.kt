package com.monika.kisshttask.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import com.monika.kisshttask.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository
) : ViewModel() {

    private val posterIdSharedFlow: MutableSharedFlow<String> = MutableSharedFlow(replay = 1)

    val posterDetailsFlow = posterIdSharedFlow.flatMapLatest {
        detailRepository.getPosterById(it)
    }

    init {
        Log.d("DetailViewModel", "init DetailViewModel")
    }

    fun loadPosterById(id: String) = posterIdSharedFlow.tryEmit(id)
}
