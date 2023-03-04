package com.example.testandroid.ui.genere

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.testandroid.data.entities.MovieEntity
import com.example.testandroid.data.repository.MovieRepository
import com.example.testandroid.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


@HiltViewModel
class AnimationViewModel @Inject constructor (private val repository: MovieRepository) : ViewModel() {

    val fetchAnimationMovies: LiveData<Resource<List<MovieEntity>>> = repository.getActionsMovies()
}