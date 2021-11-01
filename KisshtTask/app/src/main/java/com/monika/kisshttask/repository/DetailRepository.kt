package com.monika.kisshttask.repository

import androidx.annotation.WorkerThread
import com.monika.kisshttask.persistence.PosterDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val posterDao: PosterDao
) : Repository {

    @WorkerThread
    fun getPosterById(id: String) = flow {
        val poster = posterDao.getPoster(id)
        emit(poster)
    }.flowOn(Dispatchers.IO)
}
