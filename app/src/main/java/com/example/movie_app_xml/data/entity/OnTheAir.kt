package com.example.movie_app_xml.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movie_app_xml.model.Genre
import com.example.movie_app_xml.util.Const
import com.example.movie_app_xml.util.TvShow
import com.google.gson.annotations.SerializedName

@Entity
data class OnTheAir(
    @ColumnInfo(name = "vote_average")
    @field:SerializedName("vote_average")
    var voteAverage: Float? = 0.0f,

    @ColumnInfo(name = "backdrop_path")
    @field:SerializedName("backdrop_path")
    var backdropPath: String? = "",

    @ColumnInfo(name = "first_air_date")
    @field:SerializedName("first_air_date")
    var firstAirDate: String? = "",

    @ColumnInfo(name = "genres")
    @field:SerializedName("genres")
    var genres: List<Int> = listOf(),

    @ColumnInfo(name = "genresType")
    @field:SerializedName("genresType")
    var genresType: List<Genre> = listOf(),

    @ColumnInfo(name = "original_language")
    @field:SerializedName("original_language")
    var language: String? = "",

    @ColumnInfo(name = "overview")
    @field:SerializedName("overview")
    var overview: String? = "",

    @ColumnInfo(name = "popularity")
    @field:SerializedName("popularity")
    var popularity: Double? = 0.0,

    @ColumnInfo(name = "poster_path")
    @field:SerializedName("poster_path")
    var posterPath: String? = "",

    @ColumnInfo(name = "name")
    @field:SerializedName("name")
    var name: String? = "",

    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    var id: Int? = null,

    @ColumnInfo(name = "type_trending")
    var typeOnTheAir : String? = Const.TYPE_REPO_REMOTE
)
