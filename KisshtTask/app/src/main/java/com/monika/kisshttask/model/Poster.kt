package com.monika.kisshttask.model

import androidx.compose.runtime.Immutable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
@Immutable
data class Poster(
    @PrimaryKey val id: String,
    val alt_description: String?,
    @Embedded val user: User,
    @Embedded val urls: Urls
) {

    companion object {

        fun default() = Poster(
            id = "0",
            alt_description = "default",
            User("", "", ""),
            Urls("", "")
        )
    }
}

data class User(val username: String?, val name: String?, val bio: String?)
data class Urls(val raw: String?, val full: String?)
