package com.em.profile.presentation

import com.em.common.Container
import com.em.common.Core
import com.em.presentation.BaseViewModel
import com.em.profile.ProfileRouter
import com.em.profile.domain.entities.Profile
import com.em.profile.domain.repositories.FavouritesRepository
import com.example.profile.domain.GetProfileUseCase
import com.example.profile.domain.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    private val logout: LogoutUseCase,
    private val router: ProfileRouter,
    favouritesRepository: FavouritesRepository
) : BaseViewModel() {


    val stateLiveValue = combine(
        getProfileUseCase.getProfile(),
        favouritesRepository.getFavouritesItem(),
        ::merge
    ).toLiveValue()


    private fun merge(
        profile: Container<Profile>,
        size: Container<Int>
    ): Container<State> {
        return profile.map {
            State(
                profile = it,
                availableInt = size.unwrap()
            )
        }
    }

    init {
        Core.logger.log("Start init")

    }

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


    class State(
        val profile: Profile,
        val availableInt: Int
    )



}