package com.example.testandroid.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.Response
import com.example.testandroid.data.model.GetMoviesResponse
import com.example.testandroid.data.model.Movie
import com.example.testandroid.data.repository.MovieRepository
import okhttp3.internal.wait
import retrofit2.HttpException

class MoviesPagingSource(
    private val repository: MovieRepository,)
    : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        TODO("prueba")
    }


    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }

}