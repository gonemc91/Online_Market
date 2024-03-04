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
import com.em.catalog.databinding.ItemTagBinding
import com.em.catalog.domain.entitys.filter.ProductFilter
import com.em.catalog.domain.entitys.filter.SortBy
import com.em.catalog.domain.entitys.filter.SortOrder
import com.em.catalog.domain.entitys.filter.Tag
import com.em.catalog.domain.entitys.product.ProductWithInfo
import com.em.catalog.presentation.catalog.CatalogViewModel.Companion.AllTAGS
import com.em.presentation.loadResources
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

        val adapter = createCatalogAdapter()

        val adapterTags = createTagsAdapter()



        with(binding){
            observeState(adapter)
            observeStateTags(adapterTags)
            setupList(adapter)
            setupTagsList(adapterTags)
            setupListeners()
            setupSpinnerFilterSortBy()
        }
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


    private fun FragmentCatalogBinding.setupTagsList(adapter: SimpleBindingAdapter<Tag>) {
        tagsRecyclerView.simpleListHorizontal()
        tagsRecyclerView.adapter = adapter
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun createTagsAdapter() = simpleAdapter<Tag, ItemTagBinding> {
        areItemsSame = {oldItem, newItem ->  oldItem.uuidTag == newItem.uuidTag}
        areContentsSame = { oldItem, newItem -> oldItem == newItem }
        bind {

            if (it.active) {
                root.background.setTint(context?.getColor(com.em.theme.R.color.element_dark_blue)!!)
                textViewTag.setTextColor(context?.getColor(com.em.theme.R.color.text_white)!!)
                deleteTag.visibility = View.VISIBLE
            } else {
                root.background.setTint(context?.getColor(com.em.theme.R.color.background_light_grey)!!)
                textViewTag.setTextColor(context?.getColor(com.em.theme.R.color.text_grey)!!)
                deleteTag.visibility = View.GONE
            }
            textViewTag.text = it.tags
        }


        listeners {
            root.onClick {
                viewModel.toggleSelectedTAG(it.tags)
            }
        }
    }


    private fun FragmentCatalogBinding.observeState(adapter: SimpleBindingAdapter<ProductWithInfo>,
    ){
        root.observe(viewLifecycleOwner, viewModel.stateLiveValue) { state ->
            adapter.submitList(state.products)
        }
    }

    private fun FragmentCatalogBinding.observeStateTags(adapter: SimpleBindingAdapter<Tag>,
    ){
        root.observe(viewLifecycleOwner, viewModel.stateLiveValue) { state ->
            adapter.submitList(state.listTag)
        }
    }

    private fun FragmentCatalogBinding.setupList(adapter: SimpleBindingAdapter<ProductWithInfo>){
        productsRecyclerView.setupGridLayout()
        productsRecyclerView.adapter = adapter
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun createCatalogAdapter() = simpleAdapter<ProductWithInfo, ItemProductBinding> {
        areItemsSame = {oldItem, newItem ->  oldItem.product.uuid == newItem.product.uuid}
        areContentsSame = {oldItem, newItem -> oldItem == newItem}

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


            if (productWithCartInfo.favourite) {
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