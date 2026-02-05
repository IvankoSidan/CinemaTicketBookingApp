package com.ivan.cinematicketbooking.features_home.presentation

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.ivan.cinematicketbooking.core.base.BaseFragment
import com.ivan.cinematicketbooking.core.base.NavigationEvent
import com.ivan.cinematicketbooking.features_home.R
import com.ivan.cinematicketbooking.features_home.databinding.FragmentExplorerBinding
import com.ivan.cinematicketbooking.features_home.presentation.adapters.BannerSliderAdapter
import com.ivan.cinematicketbooking.features_home.presentation.adapters.MovieAdapter
import com.ivan.cinematicketbooking.nagivation.NavigatorProvider
import com.ivan.cinematicketbooking.presentation.viewmodel.MovieViewModel
import com.ivan.core.di.ViewModelFactoryProvider
import com.ivan.features_profile.presentation.viewmodel.ProfileViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs

class ExplorerFragment : BaseFragment<FragmentExplorerBinding>(FragmentExplorerBinding::inflate) {

    private val navigator by lazy {
        (requireActivity().application as NavigatorProvider).provideNavigator()
    }

    private val movieViewModel: MovieViewModel by activityViewModels {
        (requireActivity().application as ViewModelFactoryProvider).provideViewModelFactory()
    }

    private val profileViewModel: ProfileViewModel by activityViewModels {
        (requireActivity().application as ViewModelFactoryProvider).provideViewModelFactory()
    }

    private val sliderHandler = Handler(Looper.getMainLooper())
    private var sliderRunnable: Runnable? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclers()
        styleSearchView(binding.searchView)
        setupBottomNavigation()
        observeViewModel()

        profileViewModel.loadProfile()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavBar.setOnItemSelectedListener { id ->
            when (id) {
                R.id.explorerFragment -> Unit
                R.id.cartFragment -> navigator.navigateToBooking()
                R.id.profileFragment -> navigator.navigateToProfile()
            }
        }
    }

    private fun styleSearchView(searchView: SearchView) {
        searchView.setIconifiedByDefault(false)
        searchView.queryHint = getString(R.string.search_movies_hint)

        val searchEditText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        val white = ContextCompat.getColor(requireContext(), android.R.color.white)

        searchEditText.setTextColor(white)
        searchEditText.setHintTextColor(white)
        searchEditText.setTypeface(null, Typeface.BOLD)
        searchEditText.setBackgroundColor(Color.TRANSPARENT)

        val searchPlate = searchView.findViewById<View>(androidx.appcompat.R.id.search_plate)
        searchPlate?.setBackgroundColor(Color.TRANSPARENT)

        searchEditText.setPadding(
            (10 * resources.displayMetrics.density).toInt(), 0,
            (10 * resources.displayMetrics.density).toInt(), 0
        )

        val searchIcon = searchView.findViewById<View>(androidx.appcompat.R.id.search_mag_icon)
        searchIcon?.visibility = View.VISIBLE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                movieViewModel.searchResults.value.firstOrNull()?.let {
                    movieViewModel.onMovieClicked(it.id)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText?.trim().orEmpty()
                if (query.isNotEmpty()) {
                    binding.searchResults.visibility = View.VISIBLE
                    movieViewModel.search(query)
                } else {
                    binding.searchResults.visibility = View.GONE
                }
                return true
            }
        })
    }

    private fun setupBannerSlider(banners: List<com.ivan.cinematicketbooking.features_home.domain.model.Banner>) {
        if (banners.isEmpty()) return

        binding.sliderMovies.adapter = BannerSliderAdapter(banners) { id ->
            movieViewModel.onMovieClicked(id)
        }

        binding.sliderMovies.clipToPadding = false
        binding.sliderMovies.clipChildren = false
        binding.sliderMovies.offscreenPageLimit = 3
        binding.sliderMovies.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val transformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
            addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.15f
            }
        }
        binding.sliderMovies.setPageTransformer(transformer)

        sliderRunnable?.let { sliderHandler.removeCallbacks(it) }
        sliderRunnable = object : Runnable {
            override fun run() {
                if (isAdded && banners.isNotEmpty()) {
                    val nextItem = (binding.sliderMovies.currentItem + 1) % banners.size
                    binding.sliderMovies.setCurrentItem(nextItem, true)
                    sliderHandler.postDelayed(this, 3000)
                }
            }
        }
        sliderHandler.postDelayed(sliderRunnable!!, 3000)
    }

    private fun setupRecyclers() {
        binding.topMovies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.upComingMovies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.searchResults.layoutManager = LinearLayoutManager(requireContext())

        binding.topMovies.isNestedScrollingEnabled = false
        binding.upComingMovies.isNestedScrollingEnabled = false
        binding.searchResults.isNestedScrollingEnabled = false
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    movieViewModel.banners.collectLatest { setupBannerSlider(it) }
                }
                launch {
                    movieViewModel.topMovies.collectLatest { movies ->
                        binding.topMovies.adapter = MovieAdapter { id ->
                            movieViewModel.onMovieClicked(id)
                        }.apply { submitList(movies) }
                    }
                }
                launch {
                    movieViewModel.upcomingMovies.collectLatest { movies ->
                        binding.upComingMovies.adapter = MovieAdapter { id ->
                            movieViewModel.onMovieClicked(id)
                        }.apply { submitList(movies) }
                    }
                }
                launch {
                    profileViewModel.user.collectLatest { user ->
                        user?.let {
                            binding.invasionTextView.text = "Hello, ${it.username}"
                            binding.gmailTextView.text = it.email
                        }
                    }
                }
                launch {
                    movieViewModel.navigationEvent.collect { event ->
                        if (event is NavigationEvent.ToDetails) {
                            navigator.navigateToMovieDetails()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sliderRunnable?.let { sliderHandler.removeCallbacks(it) }
    }
}