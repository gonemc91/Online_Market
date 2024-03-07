package com.em.authorization.presentation

import com.em.authorization.AuthorizationRouter
import com.em.authorization.R.string
import com.em.authorization.domain.IsSignedInUseCase
import com.em.authorization.domain.SignInUseCase
import com.em.authorization.domain.entities.AuthorizationData
import com.em.authorization.domain.entities.AuthorizationFields
import com.em.authorization.domain.exceptions.EmptyFieldException
import com.em.common.Container
import com.em.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val router: AuthorizationRouter,
    private val signUpUseCase: SignInUseCase,
    private val isSignedInUseCase: IsSignedInUseCase
): BaseViewModel() {

    private val loadScreenStateFlow = MutableStateFlow<Container<Unit>>(Container.Pending)
    private val  authorizationInProgressFlow = MutableStateFlow(false)
    private val fieldErrorMessageFlow = MutableStateFlow<Pair<AuthorizationFields, String>?>(null)

    val focusFieldEventValue = liveEvent<AuthorizationFields>()
    val clearFieldEventValue = liveEvent<AuthorizationFields>()


    init {
        load()
    }

   private fun load() = debounce {
        viewModelScope.launch {
            loadScreenStateFlow.value = Container.Pending
            try {
                if(isSignedInUseCase.isSignedIn()){
                    router.launchMain()
                }else{
                    loadScreenStateFlow.value = Container.Success(Unit)
                }
            }catch (e: Exception){
                loadScreenStateFlow.value = Container.Error(e)
            }
        }
    }


    val stateLiveValue = combine(
        loadScreenStateFlow,
        authorizationInProgressFlow,
        fieldErrorMessageFlow,
        ::merge
    ).toLiveValue(Container.Pending)



    private fun merge(
        loadContainer:  Container<Unit>,
        inProgress: Boolean,
        fieldErrorMessage: Pair<AuthorizationFields, String>?,
    ): Container<State> {
        return loadContainer.map { State(inProgress,fieldErrorMessage) }
    }



    fun  authorization(signUpData: AuthorizationData) = debounce {
        viewModelScope.launch {
            try {
                inProgress()
                signUpUseCase.authorization(signUpData)
                router.launchMain()
            }catch (e: EmptyFieldException) {
                handleEmptyFieldException(e)
            }finally {
                endOfProgress()
            }
        }
    }

    fun clearError(field: AuthorizationFields){
        val currentErrorField = fieldErrorMessageFlow.value?.first
        if (currentErrorField == field){
            fieldErrorMessageFlow.value = null
        }
    }

    private fun handleEmptyFieldException(emptyFieldException: EmptyFieldException){
        focusField(emptyFieldException.field)
        setFieldError(emptyFieldException.field, resources.getString(string.author_empty_field))
    }



    private fun inProgress(){
        authorizationInProgressFlow.value = true
    }

    private fun endOfProgress(){
        authorizationInProgressFlow.value = false
    }

    private fun focusField(field: AuthorizationFields){
        focusFieldEventValue.publish(field)
    }

    private fun clearField(field: AuthorizationFields){
        clearFieldEventValue.publish(field)
    }

    private fun setFieldError(field: AuthorizationFields, errorMessage: String) {
        fieldErrorMessageFlow.value = field to errorMessage
    }


    data class State(
        val authorizationProgress: Boolean,
        val fieldErrorMessage: Pair<AuthorizationFields, String>?,
    ) {
        val enableEnterButton: Boolean get() = !authorizationProgress
    }

}