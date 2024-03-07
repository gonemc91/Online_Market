package com.em.catalog.presentation.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.elveum.elementadapter.SimpleBindingAdapter
import com.elveum.elementadapter.setTintColor
import com.elveum.elementadapter.simpleAdapter
import com.em.catalog.R
import com.em.catalog.databinding.FragmentProductDetailsBinding
import com.em.catalog.databinding.ItemInfoBinding
import com.em.catalog.domain.entitys.product.InfoProduct
import com.em.catalog.domain.entitys.product.Product
import com.em.catalog.presentation.catalog.adapters.ViewPagerAdapter
import com.em.presentation.BaseScreen
import com.em.presentation.args
import com.em.presentation.viewBinding
import com.em.presentation.viewModelCreator
import com.em.presentation.views.observe
import com.em.presentation.views.simpleList
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {
    class Screen(
        val productId: Product,
    ) : BaseScreen

    @Inject
    lateinit var factory: ProductDetailsViewModel.Factory
    private val viewModel by viewModelCreator { factory.create(args()) }

    private val binding by viewBinding<FragmentProductDetailsBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val infoProductAdapter = createInfoAdapter()

        with(binding) {
            setupProductInfoList(infoProductAdapter)
            observeState(infoProductAdapter)
            setupListeners()

        }
    }

    private fun FragmentProductDetailsBinding.observeState(
        infoAdapter: SimpleBindingAdapter<InfoProduct>,
    ) {
        root.observe(viewLifecycleOwner, viewModel.stateLiveValue) { state ->

            if (state.favoritesButtonState) {
                favoriteButton.setImageResource(com.em.theme.R.drawable.ic_type_heart__state_default)
            } else {
                favoriteButton.setImageResource(com.em.theme.R.drawable.ic_type_heart__state_active)
            }
            val product = state.productUI

            if (product.images != null) {
                val imageList = listOf(
                    product.images.image1,
                    product.images.image2
                )
                val adapter = ViewPagerAdapter()
                adapter.productImages = imageList
                productImageViewPager.adapter = adapter
                tabLayout.attachTo(productImageViewPager)


            }


            title.text = product.title
            subTitle.text = product.subtitle
            productAvailable.text =  getString(R.string.details_fragments_with_available, product.available.toString())
            val rating = product.feedback.rating.toInt()

            for (i in 0 until rating) {
                when (i) {
                    0 -> ratingImage1.setTintColor(com.em.theme.R.color.element_orange)
                    1 -> ratingImage2.setTintColor(com.em.theme.R.color.element_orange)
                    2 -> ratingImage3.setTintColor(com.em.theme.R.color.element_orange)
                    3 -> ratingImage4.setTintColor(com.em.theme.R.color.element_orange)
                    4 -> ratingImage5.setTintColor(com.em.theme.R.color.element_orange)
                }
            }
            ratingText.text = getString(
                R.string.details_fragments_feedback,
                product.feedback.rating.toString(),
                product.feedback.count.toString()
            )
            finalPriceTextView.text =
                getString(
                    R.string.details_fragments_with_space,
                    product.price.priceWithDiscount,
                    product.price.unit
                )
            originPriceTextView.text =
                getString(
                    R.string.details_fragments_with_space,
                    product.price.price.toString(),
                    product.price.unit
                )

            discountPercentage.text =
                getString(
                    R.string.catalog_fragments_with_percentage,
                    product.price.discount.toString()
                )
            labelForButton.text = product.title
            textDescription.text = product.description

            infoAdapter.submitList(product.info)

            textComposition.text = product.ingredients
            finalPriceTextViewInButton.text =
                getString(
                    R.string.details_fragments_with_space,
                    product.price.priceWithDiscount,
                    product.price.unit
                )

            originPriceTextViewInButton.text =
                getString(
                    R.string.details_fragments_with_space,
                    product.price.price.toString(),
                    product.price.unit
                )
            favoriteButton.setOnClickListener {
                viewModel.addToFavorites(product)
            }

            var isVisibleDetails = true
            buttonHideMoreDescription.setOnClickListener {
                isVisibleDetails = !isVisibleDetails


                if (isVisibleDetails) {
                    buttonLabel.visibility = View.VISIBLE
                    buttonLabelImage.visibility = View.VISIBLE
                    labelForButton.visibility = View.VISIBLE
                    textDescription.visibility = View.VISIBLE
                    buttonHideMoreDescription.text = getString(R.string.details_hide_button)
                } else {
                    buttonLabel.visibility = View.GONE
                    buttonLabelImage.visibility = View.GONE
                    labelForButton.visibility = View.GONE
                    textDescription.visibility = View.GONE
                    buttonHideMoreDescription.text = getString(R.string.details_button_text)
                }
            }

            var isVisibleComponent = true
            buttonHideMoreComposition.setOnClickListener {
                isVisibleComponent = !isVisibleComponent
                if (isVisibleComponent) {
                    textComposition.maxLines = 10
                    buttonHideMoreComposition.text = getString(R.string.details_hide_button)
                }else{
                    textComposition.lineHeight
                    textComposition.maxLines = 2
                    buttonHideMoreComposition.text = getString(R.string.details_button_text)

                }
            }

        }
    }


    private fun FragmentProductDetailsBinding.setupProductInfoList(adapter: SimpleBindingAdapter<InfoProduct>) {
        productInfoRecyclerView.simpleList()
        productInfoRecyclerView.adapter = adapter

    }

    private fun createInfoAdapter() = simpleAdapter<InfoProduct, ItemInfoBinding> {
        areItemsSame = { oldItem, newItem -> oldItem.title == newItem.title }
        areContentsSame = { oldItem, newItem -> oldItem == newItem }
        bind {
            productArticle.text = it.title
            textProductArticle.text = it.value
        }

    }


    private fun FragmentProductDetailsBinding.setupListeners() {
        root.setTryAgainListener { viewModel.reload() }
    }



    fun TabLayout.setupWithViewPager2(viewPager: ViewPager2, labels: List<String>) {

        if (labels.size != viewPager.adapter?.itemCount)
            throw Exception("The size of list and the tab count should be equal!")

        TabLayoutMediator(this, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = labels[position]
            }).attach()
    }



}