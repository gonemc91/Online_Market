package com.em.online_market.navigation.presentation.navigation


import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class NavigationModeHolder  @Inject constructor(){

    var navigateMode: NavigationMode = NavigationMode.Stack

}