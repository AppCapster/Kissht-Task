package com.monika.kisshttask.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.monika.kisshttask.model.Poster

@Dao
interface PosterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosterList(posters: List<Poster>)

    @Query("SELECT * FROM Poster WHERE id = :id_")
    suspend fun getPoster(id_: String): Poster?

    @Query("SELECT * FROM Poster")
    suspend fun getPosterList(): List<Poster>
}
