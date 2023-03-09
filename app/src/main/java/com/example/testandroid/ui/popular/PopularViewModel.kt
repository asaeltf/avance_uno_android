package com.example.testandroid.ui.popular

import android.util.Log
import androidx.lifecycle.*
import com.example.testandroid.data.entities.MovieEntity
import com.example.testandroid.data.model.ResourceStatus
import com.example.testandroid.data.repository.MovieRepository
import com.example.testandroid.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PopularViewModel @Inject constructor (private val repository: MovieRepository) : ViewModel() {
    private var currentPage = 1

    private val _popularMovies = MutableLiveData<Resource<List<MovieEntity>>>()
    val popularMovies: LiveData<Resource<List<MovieEntity>>> = _popularMovies

    init {
        loadNextPage()
    }
    fun loadNextPage() {
        _popularMovies.value = Resource.loading(null)

        viewModelScope.launch {
            val result = repository.getPopularMovies(currentPage)

            result.observeForever { response ->
                when (response.resourceStatus) {
                    ResourceStatus.SUCCESS -> {
                        val oldList = _popularMovies.value?.data ?: emptyList()
                        val newList = oldList + response.data!!
                        _popularMovies.value = Resource.success(newList)
                        currentPage++
                    }
                    ResourceStatus.ERROR -> {
                        _popularMovies.value = Resource.error(response.message ?: "Error desconocido", null)
                    }
                    ResourceStatus.LOADING -> {
                        _popularMovies.value = Resource.loading(null)
                    }
                }
            }
        }
    }




}
