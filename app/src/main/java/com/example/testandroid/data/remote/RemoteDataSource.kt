package com.example.testandroid.data.remote

import com.example.testandroid.utils.Const
import com.example.testandroid.utils.BaseDataSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiServices: ApiService) : BaseDataSource() {
    suspend fun getPopularMovies(page: Int) = getResult { apiServices.getPopularMovies(Const.API_KEY,page) }

    suspend fun getActionsMovies() = getResult { apiServices.getActionsMovies(Const.API_KEY, Const.ACTION) }

    suspend fun getTopRatedMovies() = getResult { apiServices.getTopRatedMovies(Const.API_KEY) }
}