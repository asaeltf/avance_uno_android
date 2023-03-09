package com.example.testandroid.ui.top

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
class TopViewModel @Inject constructor (private val repository: MovieRepository) : ViewModel() {
    private var currentPage = 1

    private val _topMovies = MutableLiveData<Resource<List<MovieEntity>>>()
    val topMovies: LiveData<Resource<List<MovieEntity>>> = _topMovies

    init {
        loadNextPage()
    }
    fun loadNextPage() {
        _topMovies.value = Resource.loading(null)

        viewModelScope.launch {
            val result = repository.getTopRatedMovies(currentPage)

            result.observeForever { response ->
                when (response.resourceStatus) {
                    ResourceStatus.SUCCESS -> {
                        val oldList = _topMovies.value?.data ?: emptyList()
                        val newList = oldList + response.data!!
                        _topMovies.value = Resource.success(newList)
                        currentPage++
                    }
                    ResourceStatus.ERROR -> {
                        _topMovies.value = Resource.error(response.message ?: "Error desconocido", null)
                    }
                    ResourceStatus.LOADING -> {
                        _topMovies.value = Resource.loading(null)
                    }
                }
            }
        }
    }
}