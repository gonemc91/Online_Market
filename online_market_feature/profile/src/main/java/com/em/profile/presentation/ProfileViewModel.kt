package com.em.profile.presentation

import com.em.presentation.BaseViewModel
import com.em.profile.ProfileRouter
import com.example.profile.domain.GetProfileUseCase
import com.example.profile.domain.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    private val logout: LogoutUseCase,
    private val router: ProfileRouter,
) : BaseViewModel() {

    val profileLiveValue = getProfileUseCase.getProfile()


    fun reload() = debounce{
        router.restartApp()
    }


    fun logout() = debounce {
        viewModelScope.launch{
            logout.logout()
            router.restartApp()
        }
    }

    fun launchFavorites() {
        router.launchFavouriteProductScreen()
    }

}