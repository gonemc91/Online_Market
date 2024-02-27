package com.em.online_market.navigation.presentation.navigation

import java.io.Serializable

sealed class NavigationMode: Serializable {

    /**
     * Simple stack navigation
     */

    object Stack : NavigationMode()

    /**
     *Simple stack navigation but the initial screen contains bottom tabs
     * defined in [tabs] argument.
     */

    class Tabs(
        val tabs: ArrayList<NavTab>,
        val startTabDestinationId: Int?,
    ) : NavigationMode()



}