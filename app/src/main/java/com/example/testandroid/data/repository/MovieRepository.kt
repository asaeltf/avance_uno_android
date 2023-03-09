package com.example.testandroid.data.repository

import com.example.testandroid.data.local.MovieDao
import com.example.testandroid.data.model.MovieType
import com.example.testandroid.data.model.toMovieEntityList
import com.example.testandroid.data.remote.RemoteDataSource
import javax.inject.Inject
import com.example.testandroid.utils.performGetOperation

class MovieRepository @Inject constructor(
    private val localDataSource: MovieDao,
    private val remoteDataSource: RemoteDataSource) {


    fun getPopularMovies(page:Int) = performGetOperation(
        databaseQuery = { localDataSource.getAllMovies(MovieType.POPULAR.value) },
        networkCall = { remoteDataSource.getPopularMovies(page) },
        saveCallResult = { localDataSource.insertAll(it.results.toMovieEntityList(MovieType.POPULAR.value)) }
    )

    fun getActionsMovies(page: Int) = performGetOperation(
        databaseQuery = { localDataSource.getAllMovies(MovieType.ACTION.value) },
        networkCall = { remoteDataSource.getActionsMovies(page) },
        saveCallResult = { localDataSource.insertAll(it.results.toMovieEntityList(MovieType.ACTION.value)) }
    )

    fun getTopRatedMovies(page: Int) = performGetOperation(
        databaseQuery = { localDataSource.getAllMovies(MovieType.TOP_RATED.value) },
        networkCall = { remoteDataSource.getTopRatedMovies(page) },
        saveCallResult = { localDataSource.insertAll(it.results.toMovieEntityList(MovieType.TOP_RATED.value)) }
    )
}