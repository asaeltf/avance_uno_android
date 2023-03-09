package com.example.testandroid.ui.genere

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
class AnimationViewModel @Inject constructor (private val repository: MovieRepository) : ViewModel() {
    private var currentPage = 1

    private val _animationMovies = MutableLiveData<Resource<List<MovieEntity>>>()
    val animationMovies: LiveData<Resource<List<MovieEntity>>> = _animationMovies

    init {
        loadNextPage()
    }
    fun loadNextPage() {
        _animationMovies.value = Resource.loading(null)

        viewModelScope.launch {
            val result = repository.getTopRatedMovies(currentPage)

            result.observeForever { response ->
                when (response.resourceStatus) {
                    ResourceStatus.SUCCESS -> {

                        val newList = response.data!!
                        _animationMovies.value = Resource.success(newList)
                        currentPage++
                    }
                    ResourceStatus.ERROR -> {
                        _animationMovies.value = Resource.error(response.message ?: "Error desconocido", null)
                    }
                    ResourceStatus.LOADING -> {
                        _animationMovies.value = Resource.loading(null)
                    }
                }
            }
        }
    }
}