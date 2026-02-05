package com.ivan.cinematicketbooking.features_auth.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ivan.cinematicketbooking.core.base.BaseFragment
import com.ivan.cinematicketbooking.core.base.NavigationEvent
import com.ivan.cinematicketbooking.features.auth.databinding.FragmentIntroBinding
import com.ivan.cinematicketbooking.nagivation.NavigatorProvider
import com.ivan.core.di.ViewModelFactoryProvider
import com.ivan.features_auth.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class IntroFragment : BaseFragment<FragmentIntroBinding>(FragmentIntroBinding::inflate) {

    private val navigator by lazy {
        (requireActivity().application as NavigatorProvider).provideNavigator()
    }

    private val viewModel: AuthViewModel by viewModels {
        (requireActivity().application as ViewModelFactoryProvider).provideViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonGetStarted.setOnClickListener {
            navigator.navigateToAuth()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigationEvent.collect { event ->
                    if (event is NavigationEvent.ToHome) {
                        navigator.navigateToHome()
                    }
                }
            }
        }
        viewModel.checkSession()
    }
}