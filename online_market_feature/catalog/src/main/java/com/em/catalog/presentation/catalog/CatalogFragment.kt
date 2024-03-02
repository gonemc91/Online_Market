package com.em.catalog.presentation.catalog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.elveum.elementadapter.SimpleBindingAdapter
import com.elveum.elementadapter.simpleAdapter
import com.em.catalog.R
import com.em.catalog.databinding.FragmentCatalogBinding
import com.em.catalog.databinding.ItemProductBinding
import com.em.catalog.domain.entitys.filter.ProductFilter
import com.em.catalog.domain.entitys.filter.SortBy
import com.em.catalog.domain.entitys.filter.SortOrder
import com.em.catalog.domain.entitys.product.ProductWithInfo
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
            setupListeners()
            setupSpinnerFilterSortBy()
        }
    }


    private fun FragmentCatalogBinding.observeState(adapter: SimpleBindingAdapter<ProductWithInfo>){
        root.observe(viewLifecycleOwner, viewModel.stateLiveValue) { state ->
            adapter.submitList(state.products)
        }

    }

    private fun FragmentCatalogBinding.setupList(adapter: SimpleBindingAdapter<ProductWithInfo>){
        productsRecyclerView.setupGridLayout()
        productsRecyclerView.adapter = adapter
    }

    private fun FragmentCatalogBinding.setupListeners(){
        filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val filter = when(adapterView?.adapter?.getItem(position)){
                    POPULARITY -> { ProductFilter.EMPTY.copy(sortBy = SortBy.RATING)}
                    ASCENDING_PRICE -> {ProductFilter.EMPTY.copy(sortBy = SortBy.PRICE, sortOrder =  SortOrder.ASC)}
                    DESCENDING_PRICE -> {ProductFilter.EMPTY.copy(sortBy = SortBy.PRICE, sortOrder =  SortOrder.DESC)}
                    else -> {ProductFilter.EMPTY }
                }
                viewModel.filter = filter

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

    }


    private fun FragmentCatalogBinding.setupSpinnerFilterSortBy() {
        val items = listOf(POPULARITY, ASCENDING_PRICE, DESCENDING_PRICE).toList()
        val adapter = ArrayAdapter(requireContext(),  android.R.layout.simple_list_item_1, items)
        filterSpinner.adapter = adapter
    }






    @SuppressLint("UseCompatLoadingForDrawables")
    private fun createAdapter() = simpleAdapter<ProductWithInfo, ItemProductBinding> {
        areItemsSame = {oldItem, newItem ->  oldItem.product.id == newItem.product.id}
        areContentsSame = {oldItem, newItem -> oldItem == newItem }


        bind { productWithCartInfo ->
            val product = productWithCartInfo.product

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

            Core.logger.log("${productWithCartInfo.product.title} Favorites state: ${productWithCartInfo.favourite}")

            if (productWithCartInfo.favourite) {
                favoriteButton.setImageResource(com.em.theme.R.drawable.ic_type_heart__state_active)
            } else {
                favoriteButton.setImageResource(com.em.theme.R.drawable.ic_type_heart__state_default)
            }


            listeners {
                root.onClick { productWithCartInfo ->
                    viewModel.launchDetails(productWithCartInfo)
                }
                favoriteButton.onClick {
                    val stateFavouriteButton = productWithCartInfo.favourite
                    if (stateFavouriteButton) {
                        viewModel.deleteOnFavorites(product.id)
                    }else{
                        viewModel.addToFavorites(product.id)
                    }
                }
            }
        }
    }
    companion object{
        const val DESCENDING_PRICE = "По уменьшению цены"
        const val ASCENDING_PRICE = "По возрастанию цены"
        const val POPULARITY = "По популярности"
    }
}