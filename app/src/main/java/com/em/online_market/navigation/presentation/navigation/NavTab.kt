package com.em.online_market.navigation.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import java.io.Serializable

class NavTab(

    /**
     * Destination ID which is launched when a user taps this tab.
     */
    @IdRes
    val destinationId: Int,

    /**
     * Tab name displayed under the icon.
     */
    val title: String,
    /**
     * Tab icon.
     */
    @DrawableRes
    val iconRes: Int

) : Serializable