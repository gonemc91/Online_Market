package com.em.catalog.presentation.catalog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.elveum.elementadapter.SimpleBindingAdapter
import com.elveum.elementadapter.simpleAdapter
import com.em.catalog.R
import com.em.catalog.databinding.FragmentCatalogBinding
import com.em.catalog.databinding.ItemProductBinding
import com.em.catalog.domain.entitys.product.ProductWithCartInfo
import com.em.common.Core
import com.em.presentation.loadResources
import com.em.presentation.viewBinding
import com.em.presentation.views.observe
import com.em.presentation.views.setupGridLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CatalogFragment : Fragment(R.layout.fragment_catalog) {


    private val binding by viewBinding<FragmentCatalogBinding>()

    private val viewModel by viewModels<CatalogViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = createAdapter()

        with(binding){
            observeState(adapter)
            setupList(adapter)
            //setupListeners()
        }
    }


    private fun FragmentCatalogBinding.observeState(adapter: SimpleBindingAdapter<ProductWithCartInfo>){
        root.observe(viewLifecycleOwner, viewModel.stateLiveValue) { state ->
            adapter.submitList(state.products)
        }
    }

    private fun FragmentCatalogBinding.setupList(adapter: SimpleBindingAdapter<ProductWithCartInfo>){
        productsRecyclerView.setupGridLayout()
        productsRecyclerView.adapter = adapter
    }

    private fun FragmentCatalogBinding.setupListeners(){
        TODO("Filter impl")
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun createAdapter() = simpleAdapter<ProductWithCartInfo, ItemProductBinding> {
        areItemsSame = {oldItem, newItem ->  oldItem.product.id == newItem.product.id}
        areContentsSame = {oldItem, newItem -> oldItem == newItem }

        bind { productWithCartInfo ->
            val product = productWithCartInfo.product
            Core.logger.log("Product in UI $product")
            Core.logger.log("Price ${product.price.price}")
            Core.logger.log("Price ${product.price.unit}")
            Core.logger.log("Price ${product.price.discount}")
            Core.logger.log("Price ${product.price.priceWithDiscount}")

            product.images?.image1?.let { productImageView.loadResources(it) }
            if (product.price.priceWithDiscount == null){
                originPriceTextView.isInvisible = true
                discountPercentage.isInvisible = true

                finalPriceTextView.text = buildString {
                    append(product.price.price)
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

            productNameTextView.text = product.title
            productDescription.text = product.description
            ratingText.text = product.feedback.rating.toString()
            feedbackText.text = buildString {
                append("(")
                append(product.feedback.count)
                append(")")
            }
            if (productWithCartInfo.favourite)
                favoriteButton.setImageResource(com.em.theme.R.drawable.ic_type_heart__state_active)
            else
                favoriteButton.setImageResource(0)


            listeners {
                root.onClick { productWithCartInfo ->
                    viewModel.launchDetails(productWithCartInfo)
                }
                favoriteButton.onClick {
                    val stateFavouriteButton = productWithCartInfo.favourite
                    if (stateFavouriteButton)
                        viewModel.deleteOnFavorites(productWithCartInfo.copy(favourite = false))
                    else
                        viewModel.addToFavorites(productWithCartInfo.copy(favourite = true))
                }
            }
        }
    }
}