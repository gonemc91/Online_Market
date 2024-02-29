package com.em.online_market.glue.navigation

import android.content.Context
import com.em.online_market.R
import com.em.online_market.navigation.DestinationsProvider
import com.em.online_market.navigation.presentation.navigation.NavTab
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultDestinationProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : DestinationsProvider {
    override fun provideStartDestinationId(): Int {
        return R.id.authorizationFragment
    }

    override fun provideNavigationGraphId(): Int {
        return R.navigation.nav_graph
    }

    override fun provideMainTabs(): List<NavTab> {
        return listOf(
            NavTab(
                destinationId = R.id.catalogFragment,
                title = context.getString(R.string.tabs_main),
                iconRes = com.em.theme.R.drawable.ic_type_home__state_default
            ),
            NavTab(
                destinationId = R.id.catalogFragment,
                title = context.getString(R.string.tabs_catalog),
                iconRes = com.em.theme.R.drawable.ic_type_catalog__state_default,
            ),
            NavTab(
                destinationId = R.id.catalogFragment,
                title = context.getString(R.string.tabs_cart),
                iconRes = com.em.theme.R.drawable.ic_type_bag__state_default,
            ),
            NavTab(
                destinationId = R.id.catalogFragment,
                title = context.getString(R.string.tabs_discount),
                iconRes = com.em.theme.R.drawable.ic_type_discount__state_default,
            ),
            NavTab(
                destinationId = R.id.catalogFragment,
                title = context.getString(R.string.tabs_profile),
                iconRes = com.em.theme.R.drawable.ic_type_account__state_default,
            ),
            )
    }

    override fun provideTabsDestinationId(): Int {
        return R.id.tabsFragment
    }

}