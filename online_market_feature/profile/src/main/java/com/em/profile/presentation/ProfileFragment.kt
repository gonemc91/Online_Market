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
            observeProfile()
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

    private fun FragmentProfileBinding.observeProfile(){
        root.observe(viewLifecycleOwner, viewModel.profileLiveValue){profile->
            userFullName.text = "${profile.username}" + " " + "${profile.surname}"
            userTelephone.text = profile.telephoneNumber
        }
    }




}