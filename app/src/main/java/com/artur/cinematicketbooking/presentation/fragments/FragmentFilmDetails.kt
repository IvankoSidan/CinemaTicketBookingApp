package com.artur.cinematicketbooking.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artur.cinematicketbooking.R
import com.artur.cinematicketbooking.common.HorizontalSpaceDecoration
import com.artur.cinematicketbooking.common.dpToPx
import com.artur.cinematicketbooking.presentation.adapters.CastAdapter
import com.artur.cinematicketbooking.presentation.adapters.CategoryAdapter
import com.artur.cinematicketbooking.presentation.ui.MainActivity
import com.artur.cinematicketbooking.presentation.viewmodel.MovieViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FragmentFilmDetails : Fragment() {

    private val movieViewModel: MovieViewModel by activityViewModels {
        (requireActivity() as MainActivity).viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_film_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        val backBtn = view.findViewById<ImageView>(R.id.button_back)
        val poster = view.findViewById<ImageView>(R.id.image_poster)
        val title = view.findViewById<TextView>(R.id.text_title)
        val yearDuration = view.findViewById<TextView>(R.id.text_year_duration)
        val imdb = view.findViewById<TextView>(R.id.text_imdb)
        val summary = view.findViewById<TextView>(R.id.text_summary)
        val genresRv = view.findViewById<RecyclerView>(R.id.recycler_genres)
        val castRv = view.findViewById<RecyclerView>(R.id.recycler_cast)
        val buyBtn = view.findViewById<AppCompatButton>(R.id.button_buy_ticket)
        val blurOverlay = view.findViewById<BlurView>(R.id.blur_overlay)

        val genreAdapter = CategoryAdapter()
        val castAdapter = CastAdapter()

        genresRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        castRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        genresRv.adapter = genreAdapter
        castRv.adapter = castAdapter
        castRv.addItemDecoration(HorizontalSpaceDecoration(16.dpToPx()))

        val radius = 15f
        val decorView = requireActivity().window.decorView
        val rootView = decorView.findViewById<ViewGroup>(android.R.id.content)
        val windowBackground = decorView.background

        blurOverlay.setupWith(rootView)
            .setFrameClearDrawable(windowBackground)
            .setBlurAlgorithm(RenderScriptBlur(requireContext()))
            .setBlurRadius(radius)

        blurOverlay.outlineProvider = ViewOutlineProvider.BACKGROUND
        blurOverlay.clipToOutline = true

        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.selectedMovieId.collectLatest { id ->
                id?.let {
                    movieViewModel.getMovieById(it) { movie ->
                        title.text = movie.title
                        summary.text = movie.description
                        imdb.text = "IMDb ${movie.imdbRating}"
                        yearDuration.text = buildString {
                            append(movie.year)
                            movie.duration?.let { duration ->
                                val hours = duration / 60
                                val minutes = duration % 60
                                append(" • ${hours}h ${minutes}m")
                            }
                        }

                        Glide.with(this@FragmentFilmDetails)
                            .load(movie.posterUrl)
                            .apply(
                                RequestOptions().transform(
                                    CenterCrop(),
                                    GranularRoundedCorners(0f, 0f, 50f, 50f)
                                )
                            )
                            .into(poster)

                        genreAdapter.submitList(movie.genres)
                        castAdapter.submitList(movie.cast)
                    }
                }
            }
        }

        backBtn.setOnClickListener { navController.popBackStack() }
        buyBtn.setOnClickListener {
            navController.navigate(R.id.cartFragment)
        }
    }
}
