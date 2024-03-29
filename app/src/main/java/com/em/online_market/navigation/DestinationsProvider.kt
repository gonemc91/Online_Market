package com.em.online_market.navigation

import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import com.em.online_market.navigation.presentation.navigation.NavTab

interface DestinationsProvider {

    /**
     * Get the destination ID of the initial start screen.
     */
    @IdRes
    fun provideStartDestinationId(): Int

    /**
     * Get the ID of a main navigation graph.
     */

    @NavigationRes
    fun provideNavigationGraphId(): Int

    /**
     * Get the list of tabs to be rendered at the bottom nav bar.
     */

    fun provideMainTabs() : List<NavTab>

    /**
     * Get the destination ID of [TabsFragment]
     */
    @IdRes
    fun provideTabsDestinationId(): Int

}