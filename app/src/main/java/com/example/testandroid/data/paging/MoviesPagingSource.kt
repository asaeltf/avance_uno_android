package com.example.testandroid.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testandroid.data.entities.MovieEntity
import retrofit2.Response
import com.example.testandroid.data.model.GetMoviesResponse
import com.example.testandroid.data.model.Movie
import com.example.testandroid.data.model.ResourceStatus
import com.example.testandroid.data.repository.MovieRepository
import com.example.testandroid.utils.Resource
import okhttp3.internal.wait
import retrofit2.HttpException

class MoviesPagingSource(
    private val repository: MovieRepository)
    : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        TODO()
    }


    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }

}