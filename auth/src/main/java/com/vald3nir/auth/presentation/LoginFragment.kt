package com.vald3nir.auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.auth.databinding.FragmentLoginBinding
import com.vald3nir.core_ui.CoreFragment
import com.vald3nir.core_ui.extensions.actionDoneListener
import com.vald3nir.core_ui.extensions.afterTextChanged
import com.vald3nir.core_ui.extensions.format
import com.vald3nir.core_ui.extensions.hideKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : CoreFragment() {
    private val viewModel: AuthViewModel by viewModel()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupObservers()
    }

    private fun initViews() = with(binding) {
        btnRegister.setOnClickListener { redirectToRegisterScreen() }
        btnLogin.setOnClickButtonListener { login() }
        viewModel.loadLoginData(activity)
    }

    private fun redirectToRegisterScreen() {
        navigateTo(LoginFragmentDirections.moveToRegisterFragment())
    }

    private fun setupObservers() = with(binding) {
        viewModel.loginDTO.observe(viewLifecycleOwner) {
            edtEmail.setText(it?.email)
            edtPassword.setText(it?.password)
            cbRememberLogin.isChecked = it?.rememberLogin == true
            edtEmail.afterTextChanged { loginDataChanged() }
            edtPassword.apply {
                afterTextChanged { loginDataChanged() }
                actionDoneListener { login() }
            }
        }
        viewModel.loginFormState.observe(viewLifecycleOwner) {
            val loginState = it
            edtEmailLayout.error = loginState.emailError
            edtPasswordLayout.error = loginState.passwordError
            btnLogin.hideLoading()
        }
    }

    private fun clearError() = with(binding) {
        edtEmailLayout.error = null
        edtPasswordLayout.error = null
    }

    private fun loginDataChanged() = with(binding) {
        clearError()
        viewModel.checkLoginData(
            activity,
            edtEmail.text.format(),
            edtPassword.text.format()
        )
    }

    private fun login() = with(binding) {
        hideKeyboard()
       // (activity as? AuthActivity)?.closeAuthFlow()
        viewModel.login(
            requireCoreActivity(),
            email = edtEmail.text.format(),
            password = edtPassword.text.format(),
            rememberLogin = cbRememberLogin.isChecked
        ) {
            btnLogin.hideLoading()
            showMessage(it?.message)
        }
    }
}