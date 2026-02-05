package com.ivan.cinematicketbooking.features_home.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ivan.cinematicketbooking.core.base.BaseFragment
import com.ivan.cinematicketbooking.features_home.R
import com.ivan.cinematicketbooking.features_home.databinding.FragmentFilmDetailsBinding
import com.ivan.cinematicketbooking.features_home.presentation.adapters.CastAdapter
import com.ivan.cinematicketbooking.presentation.viewmodel.MovieViewModel
import com.ivan.core.di.ViewModelFactoryProvider
import kotlinx.coroutines.launch

class FragmentFilmDetails : BaseFragment<FragmentFilmDetailsBinding>(FragmentFilmDetailsBinding::inflate) {

    private val movieViewModel: MovieViewModel by activityViewModels {
        (requireActivity().application as ViewModelFactoryProvider).provideViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val castAdapter = CastAdapter()
        binding.recyclerCast.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerCast.adapter = castAdapter

        binding.buttonBack.setOnClickListener { findNavController().popBackStack() }
        binding.buttonBuyTicket.setOnClickListener {
            findNavController().navigate(R.id.cartFragment)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieViewModel.selectedMovie.collect { movie ->
                    movie?.let {
                        binding.textTitle.text = it.title
                        binding.textSummary.text = it.description
                        binding.textImdb.text = "IMDb ${it.imdbRating}"
                        binding.textYearDuration.text = "${it.year} • ${it.duration} min"

                        Glide.with(requireContext())
                            .load(it.posterUrl)
                            .into(binding.imagePoster)

                        castAdapter.submitList(it.cast)
                    }
                }
            }
        }
    }
}