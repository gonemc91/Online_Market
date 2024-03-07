package com.em.catalog.presentation.catalog

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.em.catalog.R
import com.em.catalog.databinding.FragmentCatalogBinding
import com.em.catalog.domain.entitys.filter.ProductFilter
import com.em.catalog.domain.entitys.filter.SortBy
import com.em.catalog.domain.entitys.filter.SortOrder
import com.em.catalog.domain.entitys.filter.Tag
import com.em.catalog.domain.entitys.product.Product
import com.em.catalog.presentation.catalog.CatalogViewModel.Companion.AllTAGS
import com.em.catalog.presentation.catalog.adapters.ProductActionListener
import com.em.catalog.presentation.catalog.adapters.ProductAdapter
import com.em.catalog.presentation.catalog.adapters.TagsActionListener
import com.em.catalog.presentation.catalog.adapters.TagsAdapter
import com.em.presentation.viewBinding
import com.em.presentation.views.observe
import com.em.presentation.views.setupGridLayout
import com.em.presentation.views.simpleListHorizontal
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CatalogFragment : Fragment(R.layout.fragment_catalog) {

    private val binding by viewBinding<FragmentCatalogBinding>()

    private val viewModel by viewModels<CatalogViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.toggleSelectedTAG(AllTAGS)

        val adapterTags = TagsAdapter(object : TagsActionListener {
            override fun onRootClick(tag: Tag) {
                viewModel.toggleSelectedTAG(tag.name)
            }
        })
        val adapterProduct = ProductAdapter(object : ProductActionListener{
            override fun onProductDetails(product: Product) {
                viewModel.launchDetails(product)
            }

            override fun onFavoriteButton(product: Product) {
                viewModel.toggleFavouriteFlag(product)
            }
        }

        )
        with(binding){
            setupList(adapterProduct)
            observeState(adapterProduct)
            observeStateTags(adapterTags)
            setupTagsList(adapterTags)
            setupSpinnerListeners()
            setupSpinnerFilterSortBy()
        }
    }

    private fun FragmentCatalogBinding.setupSpinnerListeners(){

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


    private fun FragmentCatalogBinding.setupTagsList(adapter: TagsAdapter) {
        tagsRecyclerView.simpleListHorizontal()
        tagsRecyclerView.adapter = adapter
    }



    private fun FragmentCatalogBinding.observeState(adapter: ProductAdapter){
        root.observe(viewLifecycleOwner, viewModel.stateLiveValue) { state ->
            adapter.products = state.products
        }
    }

    private fun FragmentCatalogBinding.observeStateTags(adapter: TagsAdapter
    ){
        root.observe(viewLifecycleOwner, viewModel.stateLiveValue) { state ->
            adapter.tags = state.listTag
        }
    }

    private fun FragmentCatalogBinding.setupList(adapter: ProductAdapter){
        productsRecyclerView.setupGridLayout()
        productsRecyclerView.adapter = adapter
    }



    companion object {
        const val DESCENDING_PRICE = "По уменьшению цены"
        const val ASCENDING_PRICE = "По возрастанию цены"
        const val POPULARITY = "По популярности"

    }
}