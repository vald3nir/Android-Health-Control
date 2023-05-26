package com.vald3nir.auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.auth.databinding.FragmentRegisterBinding
import com.vald3nir.core_ui.CoreFragment
import com.vald3nir.core_ui.extensions.actionDoneListener
import com.vald3nir.core_ui.extensions.afterTextChanged
import com.vald3nir.core_ui.extensions.hideKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.vald3nir.commons.R as RCommons

class RegisterFragment : CoreFragment() {

    private val viewModel: AuthViewModel by viewModel()
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupObservers()
    }

    private fun initViews() = with(binding) {
        navigator.enableClickEvents(requireCoreActivity())
        navigator.updateIconsCor(RCommons.color.secondary_color)
        edtEmail.afterTextChanged { registerDataChanged() }
        edtPassword.afterTextChanged { registerDataChanged() }
        edtConfirmPassword.apply {
            afterTextChanged { registerDataChanged() }
            actionDoneListener { registerNewUser() }
        }
        btnRegister.setOnClickButtonListener { registerNewUser() }
    }

    private fun setupObservers() = with(binding) {
        viewModel.registerFormState.observe(viewLifecycleOwner) {
            val loginState = it
            edtNameLayout.error = loginState.nameError
            edtEmailLayout.error = loginState.emailError
            edtPasswordLayout.error = loginState.passwordError
            edtConfirmPasswordLayout.error = loginState.confirmPasswordError
            btnRegister.hideLoading()
        }
    }

    private fun clearError() = with(binding) {
        edtNameLayout.error = null
        edtEmailLayout.error = null
        edtPasswordLayout.error = null
        edtConfirmPasswordLayout.error = null
    }

    private fun registerDataChanged() = with(binding) {
        clearError()
        viewModel.checkRegisterData(
            requireCoreActivity(),
            name = edtName.text.toString(),
            email = edtEmail.text.toString(),
            password = edtPassword.text.toString(),
            confirmPassword = edtConfirmPassword.text.toString()
        )
    }

    private fun registerNewUser() = with(binding) {
        hideKeyboard()
        viewModel.registerNewUser(
            requireCoreActivity(),
            name = edtName.text.toString(),
            email = edtEmail.text.toString(),
            password = edtPassword.text.toString(),
            confirmPassword = edtConfirmPassword.text.toString(),
            onSuccess = {
                showMessage("Resgistro realizado com sucesso!")
                activity?.onBackPressedDispatcher?.onBackPressed()
            },
            onError = {
                btnRegister.hideLoading()
                showMessage(it?.message)
            }
        )
    }
}