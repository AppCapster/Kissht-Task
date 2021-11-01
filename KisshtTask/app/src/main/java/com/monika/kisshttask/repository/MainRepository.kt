package com.monika.kisshttask.repository

import android.util.Log
import androidx.annotation.WorkerThread
import com.monika.kisshttask.model.Poster
import com.monika.kisshttask.network.ApiService
import com.monika.kisshttask.persistence.PosterDao
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService,
    private val posterDao: PosterDao
) : Repository {

    init {
        Log.d("MainRepository", "Injection MainRepository")
    }

    @WorkerThread
    fun loadPosters(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit
    ) = flow {
        val posters: List<Poster> = posterDao.getPosterList()
        if (posters.isEmpty()) {
            // request API network call asynchronously.
            apiService.fetchList()
                // handle the case when the API request gets a success response.
                .suspendOnSuccess {
                    posterDao.insertPosterList(data)
                    emit(data)
                }
                // handle the case when the API request gets an error response.
                // e.g. internal server error.
                .onError {
                    onError(message())
                }
                // handle the case when the API request gets an exception response.
                // e.g. network connection error.
                .onException {
                    onError(message())
                }
        } else {
            emit(posters)
        }
    }.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)
}
