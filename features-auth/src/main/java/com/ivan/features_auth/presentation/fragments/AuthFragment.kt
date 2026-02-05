package com.ivan.cinematicketbooking.features_auth.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.ivan.cinematicketbooking.core.base.BaseFragment
import com.ivan.cinematicketbooking.core.base.NavigationEvent
import com.ivan.cinematicketbooking.features.auth.R
import com.ivan.cinematicketbooking.features.auth.databinding.FragmentAuthBinding
import com.ivan.cinematicketbooking.nagivation.NavigatorProvider
import com.ivan.core.di.ViewModelFactoryProvider
import com.ivan.features_auth.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class AuthFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {

    private val viewModel: AuthViewModel by viewModels {
        (requireActivity().application as ViewModelFactoryProvider).provideViewModelFactory()
    }

    private val navigator by lazy {
        (requireActivity().application as NavigatorProvider).provideNavigator()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.buttonLogin.setOnClickListener {
            val email = binding.loginEmailLayout.editText?.text.toString().trim()
            val pass = binding.loginPasswordLayout.editText?.text.toString().trim()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                viewModel.login(email, pass)
            }
        }

        binding.buttonRegister.setOnClickListener {
            val name = binding.registerNameLayout.editText?.text.toString().trim()
            val email = binding.registerEmailLayout.editText?.text.toString().trim()
            val pass = binding.registerPasswordLayout.editText?.text.toString().trim()
            if (name.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty()) {
                viewModel.register(name, email, pass)
            }
        }

        binding.goToSignUp.setOnClickListener {
            binding.loginLayout.visibility = View.GONE
            binding.registerLayout.visibility = View.VISIBLE
            binding.goToSignUpLayout.visibility = View.GONE
            binding.goToLoginLayout.visibility = View.VISIBLE
        }

        binding.goToLogin.setOnClickListener {
            binding.registerLayout.visibility = View.GONE
            binding.loginLayout.visibility = View.VISIBLE
            binding.goToLoginLayout.visibility = View.GONE
            binding.goToSignUpLayout.visibility = View.VISIBLE
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.navigationEvent.collect { event ->
                        if (event is NavigationEvent.ToHome) {
                            navigator.navigateToHome()                        }
                    }
                }
                launch {
                    viewModel.errorFlow.collect { error ->
                        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}