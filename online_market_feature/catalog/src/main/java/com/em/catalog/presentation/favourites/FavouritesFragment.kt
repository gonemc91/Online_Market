package com.em.catalog.presentation.favourites

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.elveum.elementadapter.SimpleBindingAdapter
import com.elveum.elementadapter.simpleAdapter
import com.em.catalog.R
import com.em.catalog.databinding.FragmentFavouritesBinding
import com.em.catalog.databinding.ItemProductBinding
import com.em.catalog.domain.entitys.filter.ProductFilter
import com.em.catalog.domain.entitys.filter.SortBy
import com.em.catalog.domain.entitys.product.Product
import com.em.presentation.loadResources
import com.em.presentation.viewBinding
import com.em.presentation.views.observe
import com.em.presentation.views.setupGridLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavouritesFragment : Fragment(R.layout.fragment_favourites) {

    private val binding by viewBinding<FragmentFavouritesBinding>()

    private val viewModel by viewModels<FavouritesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = createCatalogAdapter()
        with(binding){
            observeState(adapter)
            setupList(adapter)
            setupListeners()

        }
    }


    private fun FragmentFavouritesBinding.setupListeners() {
        productButtonFilter.setOnClickListener {
            if (productButtonFilter.isEnabled){
                productButtonFilter.setTextColor(context?.getColor(com.em.theme.R.color.text_black)!!)
                viewModel.filter = ProductFilter.EMPTY.copy(sortBy = SortBy.RATING)
            } else{
                productButtonFilter.setTextColor(context?.getColor(com.em.theme.R.color.text_white)!!)
                viewModel.filter = ProductFilter.EMPTY.copy(sortBy = SortBy.NAME)
            }
        }

    }


    private fun FragmentFavouritesBinding.observeState(adapter: SimpleBindingAdapter<Product>,
    ){
        root.observe(viewLifecycleOwner, viewModel.stateLiveValue) { state ->
            adapter.submitList(state.products)
        }
    }


    private fun FragmentFavouritesBinding.setupList(adapter: SimpleBindingAdapter<Product>){
        productsRecyclerView.setupGridLayout()
        productsRecyclerView.adapter = adapter
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun createCatalogAdapter() = simpleAdapter<Product, ItemProductBinding> {
        areItemsSame = {oldItem, newItem ->  oldItem.uuid == newItem.uuid}
        areContentsSame = {oldItem, newItem -> oldItem == newItem }


        bind { product ->


            product.images?.image1?.let { productImageView.loadResources(it) }
            if (product.price.priceWithDiscount == null){
                originPriceTextView.isInvisible = true
                discountPercentage.isInvisible = true

                finalPriceTextView.text = buildString {
                    append(product.price.price.toString())
                    append(" ")
                    append(product.price.unit)
                }
            }else{
                originPriceTextView.visibility = View.VISIBLE
                discountPercentage.visibility = View.VISIBLE

                originPriceTextView.text = buildString {
                    append(product.price.price)
                    append(" ")
                    append(product.price.unit)
                }

                finalPriceTextView.text = buildString {
                    append(product.price.priceWithDiscount)
                    append(" ")
                    append(product.price.unit)
                }

                discountPercentage.text = buildString {
                    append("-")
                    append(product.price.discount.toString())
                    append("%")
                }

            }
            subTitle.text = product.title
            productDescription.text = product.description
            ratingText.text = product.feedback.rating.toString()
            feedbackText.text = buildString {
                append("(")
                append(product.feedback.count)
                append(")")
            }


            if (product.favourite) {
                favoriteButton.setImageResource(com.em.theme.R.drawable.ic_type_heart__state_active)
            } else {
                favoriteButton.setImageResource(com.em.theme.R.drawable.ic_type_heart__state_default)
            }


            listeners {
                root.onClick { productWithCartInfo ->
                    viewModel.launchDetails(productWithCartInfo)
                }
                favoriteButton.onClick {viewModel.toggleFavouriteFlag(it)}
            }
        }
    }


    companion object {
        const val DESCENDING_PRICE = "По уменьшению цены"
        const val ASCENDING_PRICE = "По возрастанию цены"
        const val POPULARITY = "По популярности"

    }
}