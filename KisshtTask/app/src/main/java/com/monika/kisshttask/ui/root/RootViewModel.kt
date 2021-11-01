package com.monika.kisshttask.ui.root

import android.util.Log
import androidx.lifecycle.ViewModel
import coil.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    val imageLoader: ImageLoader
) : ViewModel() {
    init {
        Log.d("RootViewModel","init RootViewModel")
    }
}