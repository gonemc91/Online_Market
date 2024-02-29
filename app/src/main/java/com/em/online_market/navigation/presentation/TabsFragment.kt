package com.em.online_market.navigation.presentation

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.em.online_market.R
import com.em.online_market.databinding.FragmentTabsBinding
import com.em.online_market.navigation.DestinationsProvider
import com.em.online_market.navigation.presentation.navigation.NavigationMode
import com.em.online_market.navigation.presentation.navigation.NavigationModeHolder
import com.em.presentation.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TabsFragment: Fragment(R.layout.fragment_tabs) {

     @Inject
    lateinit var destinationsProvider: DestinationsProvider

    @Inject
    lateinit var navigationModeHolder: NavigationModeHolder

    private val binding by viewBinding<FragmentTabsBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navigationMode = navigationModeHolder.navigateMode
        if (navigationMode is NavigationMode.Tabs){
            val menu = binding.bottomNavigationView.menu
            navigationMode.tabs.forEach{tab ->
                val menuItem = menu.add(0, tab.destinationId, Menu.FIRST, tab.title)
                menuItem.setIcon(tab.iconRes)
            }
            val navHost = childFragmentManager.findFragmentById(R.id.tabsContainer) as NavHostFragment
            val navController = navHost.navController
            val graph = navController.navInflater.inflate(destinationsProvider.provideNavigationGraphId())
            graph.setStartDestination(navigationMode.startTabDestinationId ?: navigationMode.tabs.first().destinationId)
            navController.graph = graph
            NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
        }

    }

}