package com.govindtank.tmdbapp.ui.details

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.govindtank.tmdbapp.R
import com.govindtank.tmdbapp.app.GlideApp
import com.govindtank.tmdbapp.app.Strings
import com.govindtank.tmdbapp.core.Resource
import com.govindtank.tmdbapp.data.model.MovieEntity
import com.govindtank.tmdbapp.databinding.FragmentDetailsBinding
import com.govindtank.tmdbapp.presentation.MainViewModel
import com.govindtank.tmdbapp.util.asUrl
import com.govindtank.tmdbapp.util.themeColor
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details.view.*
import kotlinx.android.synthetic.main.loading_details.view.*

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(requireContext().themeColor(R.attr.colorSurface))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsBinding.bind(view)
        loading()
        initViews()
        fetchMovie()
    }

    private fun initViews() = binding.apply {
        arrayOf(
            this.motionLayout.navigationIcon,
            shimmerLayout.loading.navigationIcon,
            binding.defaultNavigationIcon
        ).forEach {
            it.setOnClickListener { navigateUp() }
        }
        motionLayout.floatingActionButton.setOnClickListener {
            viewModel.saveToWatchLater(args.movieId)
        }
    }

    private fun fetchMovie() =
        viewModel.getMovie(args.movieId).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> loading()
                is Resource.Success -> {
                    notLoading()
                    setData(result.data)
                }
                is Resource.Error -> {
                    error()
                    binding.errorText.text = result.message
                    setData(result.data)
                }
            }
        }

    private fun setData(movie: MovieEntity?) = movie?.let {
        binding.apply {
            GlideApp.with(requireContext())
                .load(it.backdropPath.asUrl())
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_error)
                .into(motionLayout.backdropImage)

            GlideApp.with(requireContext())
                .load(it.posterPath.asUrl())
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_error)
                .into(motionLayout.posterImage)

            motionLayout.title.text = if (it.title.isEmpty()) it.name else it.title
            motionLayout.genres.text = it.genres ?: ""

            nestedScrolling.rating.text = it.rating.toString()
            nestedScrolling.language.text = it.originalLanguage
            val revenue = "${(it.revenue ?: 0) / 1_000_000}M"
            nestedScrolling.revenue.text = revenue
            val runtime = "${it.runtime}m"
            nestedScrolling.runtime.text = runtime

            nestedScrolling.releaseDateText.text = Strings.get(R.string.relase_date_placeholder, it.releaseDate)

            nestedScrolling.overview.text = it.overview

            motionLayout.floatingActionButton.setImageResource(if (it.watchLater) R.drawable.ic_playlist_add_check else R.drawable.ic_playlist_add)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun loading() = binding.apply {
        shimmerLayout.startShimmer()
        shimmerLayout.isVisible = true
        nestedScrolling.nested.isVisible = false
        appBarLayout.isVisible = false
        errorLayout.isVisible = false
    }

    private fun notLoading() = binding.apply {
        shimmerLayout.stopShimmer()
        shimmerLayout.isVisible = false
        nestedScrolling.nested.isVisible = true
        appBarLayout.isVisible = true
        errorLayout.isVisible = false
    }

    private fun error() = binding.apply {
        shimmerLayout.stopShimmer()
        shimmerLayout.isVisible = false
        nestedScrolling.nested.isVisible = false
        appBarLayout.isVisible = false
        errorLayout.isVisible = true
    }

    private fun navigateUp() {
        viewModel.clearMovie()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearMovie()
    }
}