package com.em.catalog.presentation.favourites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.em.catalog.R
import com.em.catalog.databinding.FragmentFavouritesBinding
import com.em.catalog.domain.entitys.product.Product
import com.em.catalog.presentation.catalog.adapters.ProductActionListener
import com.em.catalog.presentation.catalog.adapters.ProductAdapter
import com.em.presentation.viewBinding
import com.em.presentation.views.observe
import com.em.presentation.views.setupGridLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavouritesFragment : Fragment(R.layout.fragment_favourites) {

    private val binding by viewBinding<FragmentFavouritesBinding>()

    private val viewModel by viewModels<FavouritesViewModel>()


    private val adapterProduct = ProductAdapter(object : ProductActionListener {
        override fun onProductDetails(product: Product) {
            viewModel.launchDetails(product)
        }

        override fun onFavoriteButton(product: Product) {
            viewModel.toggleFavouriteFlag(product)
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            observeState(adapterProduct)
            setupList(adapterProduct)
        }
    }


    private fun FragmentFavouritesBinding.observeState(adapter: ProductAdapter, ){
        root.observe(viewLifecycleOwner, viewModel.stateLiveValue) { state ->
            adapter.products = state.products
        }
    }


    private fun FragmentFavouritesBinding.setupList(adapter:ProductAdapter){
        productsRecyclerView.setupGridLayout()
        productsRecyclerView.adapter = adapter
    }
}