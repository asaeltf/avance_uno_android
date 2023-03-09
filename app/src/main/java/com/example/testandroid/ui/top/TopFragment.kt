package com.example.testandroid.ui.top


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testandroid.R
import com.example.testandroid.data.entities.MovieEntity
import com.example.testandroid.data.model.Movie
import com.example.testandroid.data.model.ResourceStatus
import com.example.testandroid.databinding.FragmentTopBinding
import com.example.testandroid.ui.popular.PopularViewModel
import com.example.testandroid.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TopFragment : Fragment(), TopMovieItemAdapter.OnMovieClickListener {

    private var _binding: FragmentTopBinding? = null

    private val binding get() = _binding!!
    private var loading = true

    private val viewModel: TopViewModel by navGraphViewModels(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }

    private lateinit var topMovieItemAdapter: TopMovieItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTopBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvMovies2.layoutManager = LinearLayoutManager(context)
        val recyclerView = binding.rvMovies2
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItem >= totalItemCount && firstVisibleItem >= 0 && loading) {
                    loading = true
                    Log.e("fetchPopularMovies", "El usuario llego al final de la lista y scroll")
                    viewModel.loadNextPage()
                }
            }
        })

        viewModel.topMovies.observe(viewLifecycleOwner, Observer {
            when (it.resourceStatus) {
                ResourceStatus.LOADING -> {
                    Log.e("fetchTopMovies", "Loading")
                }
                ResourceStatus.SUCCESS  -> {
                    Log.e("fetchTopMovies", "Success")
                    topMovieItemAdapter = TopMovieItemAdapter(it.data!!, this@TopFragment)
                    binding.rvMovies2.adapter = topMovieItemAdapter
                }
                ResourceStatus.ERROR -> {
                    Log.e("fetchTopMovies", "Failure: ${it.message} ")
                    Toast.makeText(requireContext(), "Failure: ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMovieClick(movieEntity: MovieEntity) {
        val action = TopFragmentDirections.actionTopFragmentToDetailFragment(movieEntity)
        findNavController().navigate(action)
    }
}