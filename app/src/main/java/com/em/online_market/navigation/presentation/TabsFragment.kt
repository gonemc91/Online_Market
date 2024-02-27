package com.em.online_market.navigation.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.em.online_market.R
import com.em.online_market.databinding.FragmentTabsBinding
import com.em.online_market.navigation.DestinationsProvider
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

    }

}