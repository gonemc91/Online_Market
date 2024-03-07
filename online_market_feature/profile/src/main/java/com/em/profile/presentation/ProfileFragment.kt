package com.em.profile.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.em.presentation.viewBinding
import com.em.presentation.views.observe
import com.em.settings.R
import com.em.settings.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment @Inject constructor():Fragment(R.layout.fragment_profile) {


    private val viewModel by viewModels<ProfileViewModel>()

    private val binding by viewBinding<FragmentProfileBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            setupListeners()
            observeState()
        }

    }


    private fun FragmentProfileBinding.setupListeners(){
        buttonExit.setOnClickListener {
            viewModel.logout()
        }
        favourites.setOnClickListener {
            viewModel.launchFavorites()
        }


        root.setTryAgainListener { viewModel.reload() }
    }

    private fun FragmentProfileBinding.observeState(){
        root.observe(viewLifecycleOwner, viewModel.stateLiveValue){ state ->
            userFullName.text = "${state.profile.username}" + " " + "${state.profile.surname}"
            userTelephone.text = state.profile.telephoneNumber

            availableProduct.text = when (state.availableInt) {
                0 -> ""
                1 -> getString(R.string.settings_fragment_product_1, state.availableInt)
                2 -> getString(R.string.settings_fragment_product_2, state.availableInt)
                3 -> getString(R.string.settings_fragment_product_2, state.availableInt)
                4 -> getString(R.string.settings_fragment_product_2, state.availableInt)
                5 -> getString(R.string.settings_fragment_product_3, state.availableInt)
                else -> getString(R.string.settings_fragment_product_3, state.availableInt)
            }

        }
    }




}