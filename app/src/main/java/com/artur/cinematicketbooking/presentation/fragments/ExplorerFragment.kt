package com.artur.cinematicketbooking.presentation.fragments

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.artur.cinematicketbooking.R
import com.artur.cinematicketbooking.presentation.adapters.BannerSliderAdapter
import com.artur.cinematicketbooking.presentation.adapters.MovieAdapter
import com.artur.cinematicketbooking.presentation.ui.MainActivity
import com.artur.cinematicketbooking.presentation.viewmodel.MovieViewModel
import com.artur.cinematicketbooking.presentation.viewmodel.ProfileViewModel
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs

class ExplorerFragment : Fragment() {

    private val movieViewModel: MovieViewModel by activityViewModels {
        (requireActivity() as MainActivity).viewModelFactory
    }

    private val profileViewModel: ProfileViewModel by activityViewModels {
        (requireActivity() as MainActivity).viewModelFactory
    }

    private val sliderHandler = Handler(Looper.getMainLooper())
    private var sliderRunnable: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_explorer, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val bottomNav = view.findViewById<ChipNavigationBar>(R.id.bottom_nav_bar)
        val slider = view.findViewById<ViewPager2>(R.id.sliderMovies)
        val invasionTextView = view.findViewById<TextView>(R.id.invasionTextView)
        val gmailTextView = view.findViewById<TextView>(R.id.gmailTextView)
        val searchView = view.findViewById<SearchView>(R.id.searchView)
        val topMoviesRv = view.findViewById<RecyclerView>(R.id.topMovies)
        val upcomingMoviesRv = view.findViewById<RecyclerView>(R.id.upComingMovies)
        val searchResultsRv = view.findViewById<RecyclerView>(R.id.searchResults)

        bottomNav.setOnItemSelectedListener { id ->
            val currentId = navController.currentDestination?.id
            if (currentId != id) navController.navigate(id)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            profileViewModel.user.collectLatest { user ->
                user?.let {
                    invasionTextView.text = getString(R.string.hello_user, it.username)
                    gmailTextView.text = it.email
                }
            }
        }
        profileViewModel.loadProfile()

        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.banners.collectLatest { banners ->
                if (banners.isNotEmpty()) {
                    slider.adapter = BannerSliderAdapter(banners) { movieId ->
                        movieViewModel.selectMovie(movieId)
                        navController.navigate(R.id.filmDetailsFragment)
                    }

                    slider.clipToPadding = false
                    slider.clipChildren = false
                    slider.offscreenPageLimit = 3
                    slider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

                    val transformer = CompositePageTransformer().apply {
                        addTransformer(MarginPageTransformer(40))
                        addTransformer { page, position ->
                            val r = 1 - abs(position)
                            page.scaleY = 0.85f + r * 0.15f
                        }
                    }
                    slider.setPageTransformer(transformer)

                    sliderRunnable = object : Runnable {
                        override fun run() {
                            val nextItem = (slider.currentItem + 1) % banners.size
                            slider.setCurrentItem(nextItem, true)
                            sliderHandler.postDelayed(this, 3000)
                        }
                    }
                    sliderHandler.postDelayed(sliderRunnable!!, 3000)
                }
            }
        }
        movieViewModel.loadBanners()

        val topAdapter = MovieAdapter { movieId ->
            movieViewModel.selectMovie(movieId)
            navController.navigate(R.id.filmDetailsFragment)
        }
        val upcomingAdapter = MovieAdapter { movieId ->
            movieViewModel.selectMovie(movieId)
            navController.navigate(R.id.filmDetailsFragment)
        }
        val searchAdapter = MovieAdapter { movieId ->
            movieViewModel.selectMovie(movieId)
            navController.navigate(R.id.filmDetailsFragment)
        }

        topMoviesRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        upcomingMoviesRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        searchResultsRv.layoutManager = LinearLayoutManager(requireContext())

        topMoviesRv.adapter = topAdapter
        upcomingMoviesRv.adapter = upcomingAdapter
        searchResultsRv.adapter = searchAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.topMovies.collectLatest { topAdapter.submitList(it) }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.upcomingMovies.collectLatest { upcomingAdapter.submitList(it) }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.searchResults.collectLatest { searchAdapter.submitList(it) }
        }

        styleSearchView(searchView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val firstMovie = movieViewModel.searchResults.value.firstOrNull()
                firstMovie?.let {
                    movieViewModel.selectMovie(it.id)
                    findNavController().navigate(R.id.filmDetailsFragment)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText?.trim().orEmpty()
                if (query.isNotEmpty()) {
                    searchResultsRv.visibility = View.VISIBLE
                    movieViewModel.searchMovies(query)
                } else {
                    searchResultsRv.visibility = View.GONE
                    movieViewModel.clearSearch()
                }
                return true
            }
        })
    }

    private fun styleSearchView(searchView: SearchView) {
        searchView.setIconifiedByDefault(false)
        searchView.queryHint = getString(R.string.search_movies_hint)

        val searchEditText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        val white = ContextCompat.getColor(requireContext(), R.color.white)
        searchEditText.setTextColor(white)
        searchEditText.setHintTextColor(white)
        searchEditText.setTypeface(null, Typeface.BOLD)
        searchEditText.setBackgroundColor(Color.TRANSPARENT)
        searchEditText.setPadding(
            (10 * resources.displayMetrics.density).toInt(), 0,
            (10 * resources.displayMetrics.density).toInt(), 0
        )

        val searchIcon = searchView.findViewById<View>(androidx.appcompat.R.id.search_mag_icon)
        searchIcon?.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sliderRunnable?.let { sliderHandler.removeCallbacks(it) }
    }
}
