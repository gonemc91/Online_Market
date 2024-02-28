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
        /*    NavTab(
                destinationId = R.id.catalogFragment,
                title = context.getString(R.string.tab_catalog),
                iconRes = R.drawable.ic_catalog,
            ),*/

        )
    }
    override fun provideTabsDestinationId(): Int {
        return R.id.tabsFragment
    }

}