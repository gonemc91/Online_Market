package com.em.catalog.presentation.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.elveum.elementadapter.SimpleBindingAdapter
import com.elveum.elementadapter.setTintColor
import com.elveum.elementadapter.simpleAdapter
import com.em.catalog.R
import com.em.catalog.databinding.FragmentProductDetailsBinding
import com.em.catalog.databinding.ItemInfoBinding
import com.em.catalog.domain.entitys.product.InfoProduct
import com.em.catalog.domain.entitys.product.ProductWithInfo
import com.em.presentation.BaseScreen
import com.em.presentation.args
import com.em.presentation.loadResources
import com.em.presentation.viewBinding
import com.em.presentation.viewModelCreator
import com.em.presentation.views.observe
import com.em.presentation.views.simpleList
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ProductDetailsFragment: Fragment(R.layout.fragment_product_details) {
    class Screen(
        val productId: ProductWithInfo,
    ) : BaseScreen

    @Inject
    lateinit var factory: ProductDetailsViewModel.Factory
    private val viewModel by viewModelCreator { factory.create(args()) }

    private val binding by viewBinding<FragmentProductDetailsBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = createAdapter()
        with(binding){
            setupList(adapter)
            observeState(adapter)
            setupListeners()
        }
    }

    private fun FragmentProductDetailsBinding.observeState(adapter: SimpleBindingAdapter<InfoProduct>) {
        root.observe(viewLifecycleOwner, viewModel.stateLiveValue) { state ->

            if (state.favoritesButtonState){
                favoriteButton.setImageResource(com.em.theme.R.drawable.ic_type_heart__state_default)
            }else{
                favoriteButton.setImageResource(com.em.theme.R.drawable.ic_type_heart__state_active)
            }
            val product = state.product
            product.images?.image1?.let { productImageView.loadResources(it) }
            title.text = product.title
            subTitle.text = product.subtitle
            productAvailable.text = "Доступно для заказа ${product.available} штук"
            val rating = product.feedback.rating.toInt()

           for (i in 0 until rating){
                when (i) {
                    0 -> ratingImage1.setTintColor(com.em.theme.R.color.element_orange)
                    1 -> ratingImage2.setTintColor(com.em.theme.R.color.element_orange)
                    2 -> ratingImage3.setTintColor(com.em.theme.R.color.element_orange)
                    3 -> ratingImage4.setTintColor(com.em.theme.R.color.element_orange)
                    4 -> ratingImage5.setTintColor(com.em.theme.R.color.element_orange)
                }

            }
            ratingText.text = "${product.feedback.rating} · ${product.feedback.count} отзывы"
            finalPriceTextView.text = buildString {
                append(product.price.priceWithDiscount)
                append(" ")
                append(product.price.unit)


                originPriceTextView.text = buildString {
                    append(product.price.price)
                    append(" ")
                    append(product.price.unit)
                }

                discountPercentage.text = "- ${product.price.discount}%"
                labelForButton.text = product.title
                textDescription.text = product.description

                adapter.submitList(product.info)

                textComposition.text = product.ingredients
                finalPriceTextViewInButton.text = buildString {
                    append(product.price.priceWithDiscount)
                    append(" ")
                    append(product.price.unit)
                }
                originPriceTextViewInButton.text = buildString {
                    append(product.price.price)
                    append(" ")
                    append(product.price.unit)
                }
                favoriteButton.setOnClickListener{
                    viewModel.addToFavorites(product)
                }
            }
        }
    }


    private fun FragmentProductDetailsBinding.setupList(adapter: SimpleBindingAdapter<InfoProduct>) {
        productInfoRecyclerView.simpleList()
        productInfoRecyclerView.adapter = adapter
    }

    private fun createAdapter() = simpleAdapter<InfoProduct, ItemInfoBinding> {
        areItemsSame = {oldItem, newItem ->  oldItem.title == newItem.title}
        areContentsSame = {oldItem, newItem -> oldItem == newItem }

        bind {
            productArticle.text = it.title
            textProductArticle.text = it.value
        }

    }

    private fun FragmentProductDetailsBinding.setupListeners() {
        root.setTryAgainListener { viewModel.reload() }

    }


}