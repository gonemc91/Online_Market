package com.em.authorization.presentation

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.em.authorization.R.layout
import com.em.authorization.databinding.FragmentAuthorizationBinding
import com.em.authorization.domain.entities.AuthorizationData
import com.em.authorization.domain.entities.AuthorizationFields
import com.em.presentation.live.observeEvent
import com.em.presentation.viewBinding
import com.em.presentation.views.observe
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthorizationFragment: Fragment(layout.fragment_authorization) {

    private val viewModel by viewModels<AuthorizationViewModel>()
    private val binding by viewBinding<FragmentAuthorizationBinding>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeEvents(savedInstanceState)
        with(binding){
            observeScreenState()
            setupListeners()
        }
    }
    private fun observeEvents(savedInstanceState: Bundle?){

        viewModel.clearFieldEventValue.observeEvent(viewLifecycleOwner){
            getTextInputByField(it).text.clear()
        }
        viewModel.focusFieldEventValue.observeEvent(viewLifecycleOwner){
            getTextInputByField(it).requestFocus()
            getTextInputByField(it).selectAll()
        }
    }

    private fun FragmentAuthorizationBinding.observeScreenState() {
        root.observe(viewLifecycleOwner, viewModel.stateLiveValue) { state ->
                enterButton.isEnabled = state.enableEnterButton
                cleaUpErrors()
                if (state.fieldErrorMessage != null) {
                    isInvisibleDeleteButton(state.fieldErrorMessage)
                    val textInput = getTextInputByField(state.fieldErrorMessage.first)
                    textInput.error = state.fieldErrorMessage.second
                }
            }
        }




    private fun FragmentAuthorizationBinding.isInvisibleDeleteButton(
        fieldErrorMessage: Pair<AuthorizationFields, String>
    ) {
        when (fieldErrorMessage.first) {
            AuthorizationFields.USERNAME -> deleteButtonName.isInvisible = true
            AuthorizationFields.SURNAME -> deleteButtonSurname.isInvisible = true
            AuthorizationFields.TELEPHONE_NUMBER -> deleteButtonTelephoneNumber.isInvisible= true
        }
    }


    private fun FragmentAuthorizationBinding.setupListeners(){
        deleteButtonName.setOnClickListener{
            usernameTextInput.text.clear()
        }
        deleteButtonSurname.setOnClickListener{
            surnameTextInput.text.clear()
        }
        deleteButtonTelephoneNumber.setOnClickListener {
           telephoneNumberTextInput.text?.clear()
        }
        enterButton.setOnClickListener {
            viewModel.authorization(createSignUpDate())
            usernameTextInput.addTextChangedListener {viewModel.clearError(AuthorizationFields.USERNAME)}
            surnameTextInput.addTextChangedListener {viewModel.clearError(AuthorizationFields.SURNAME)}
            telephoneNumberTextInput.addTextChangedListener {
                viewModel.clearError(AuthorizationFields.TELEPHONE_NUMBER)

            }
        }
    }



    private fun FragmentAuthorizationBinding.createSignUpDate(): AuthorizationData{
        return AuthorizationData(
            username = usernameTextInput.text.toString(),
            surname = surnameTextInput.text.toString(),
            telephoneNumber = telephoneNumberTextInput.text.toString()
        )
    }


    private fun FragmentAuthorizationBinding.cleaUpErrors() {
        usernameTextInput.error = null
        surnameTextInput.error = null
        telephoneNumberTextInput.error = null
        deleteButtonName.isVisible = true
        deleteButtonSurname.isVisible = true
        deleteButtonTelephoneNumber.isVisible = true
    }



    private fun getTextInputByField(field:  AuthorizationFields): EditText {
        return when(field){
            AuthorizationFields.USERNAME -> binding.usernameTextInput
            AuthorizationFields.SURNAME -> binding.surnameTextInput
            AuthorizationFields.TELEPHONE_NUMBER -> binding.telephoneNumberTextInput
        }
    }
}