package com.artur.cinematicketbooking.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.artur.cinematicketbooking.R
import com.artur.cinematicketbooking.data.api.RetrofitInstance
import com.artur.cinematicketbooking.databinding.FragmentAuthBinding
import com.artur.cinematicketbooking.presentation.ui.MainActivity
import com.artur.cinematicketbooking.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels {
        (requireActivity() as MainActivity).viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = FragmentAuthBinding.inflate(inflater, container, false).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener {
            // ИЗМЕНЕНИЕ: Получаем текст из вложенного EditText
            val email = binding.loginEmailLayout.editText?.text.toString().trim()
            val password = binding.loginPasswordLayout.editText?.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.login(email, password)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter email and password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.buttonRegister.setOnClickListener {
            // ИЗМЕНЕНИЕ: Получаем текст из вложенного EditText
            val name = binding.registerNameLayout.editText?.text.toString().trim()
            val email = binding.registerEmailLayout.editText?.text.toString().trim()
            val password = binding.registerPasswordLayout.editText?.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.register(name, email, password)
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.forgotPassword.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Forgot password is not implemented yet",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.goToSignUp.setOnClickListener {
            binding.loginLayout.visibility = View.GONE
            binding.goToSignUpLayout.visibility = View.GONE
            binding.registerLayout.visibility = View.VISIBLE
            binding.goToLoginLayout.visibility = View.VISIBLE
        }

        binding.goToLogin.setOnClickListener {
            binding.registerLayout.visibility = View.GONE
            binding.goToLoginLayout.visibility = View.GONE
            binding.loginLayout.visibility = View.VISIBLE
            binding.goToSignUpLayout.visibility = View.VISIBLE
        }

        viewLifecycleOwner.lifecycleScope.launch {
            authViewModel.authState.collectLatest { result ->
                result?.onSuccess { response ->
                    val token = response.accessToken
                    val prefs = requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE)
                    prefs.edit().putString("access_token", token).apply()

                    RetrofitInstance.updateTokenProvider {
                        prefs.getString("access_token", null)
                    }

                    findNavController().navigate(R.id.explorerFragment)
                }?.onFailure {
                    Toast.makeText(
                        requireContext(),
                        "Authentication failed: ${it.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}