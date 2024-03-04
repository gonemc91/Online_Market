package com.em.catalog.presentation.catalog.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.elveum.elementadapter.getString
import com.em.catalog.R
import com.em.catalog.databinding.ItemProductBinding
import com.em.catalog.domain.entitys.product.Product
import com.em.presentation.loadResources

interface UserActionListener {


    fun onUserDetails(user: Product)

}

class ProductAdapter(
    private val actionListener: UserActionListener
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(), View.OnClickListener {

    var products: List<Product> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onClick(v: View) {
        val user = v.tag as Product
        when (v.id) {
            R.id.favoriteButton -> {

            }
            else -> {
                actionListener.onUserDetails(user)
            }
        }
    }

    override fun getItemCount(): Int = products.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)




        binding.root.setOnClickListener(this)
        binding.favoriteButton.setOnClickListener(this)

        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        with(holder.binding) {
            holder.itemView.tag = product
            favoriteButton.tag = product


            product.images?.image1?.let { productImageView.loadResources(it) }

            if (product.price.priceWithDiscount == null){
                originPriceTextView.isInvisible = true
                discountPercentage.isInvisible = true

                finalPriceTextView.text = getString(
                    R.string.catalog_fragments_with_space,
                    product.price.price.toString(),
                    product.price.unit
                )

            }else{
                originPriceTextView.visibility = View.VISIBLE
                discountPercentage.visibility = View.VISIBLE

                originPriceTextView.text = getString(
                    R.string.catalog_fragments_with_space,
                    product.price.price.toString(),
                    product.price.unit
                )

                finalPriceTextView.text = getString(
                    R.string.catalog_fragments_with_space,
                    product.price.priceWithDiscount,
                    product.price.unit
                )

                discountPercentage.text = getString(
                    R.string.catalog_fragments_with_percentage,
                    product.price.discount.toString())
            }

            subTitle.text = product.title
            productDescription.text = product.description
            ratingText.text = product.feedback.rating.toString()
            feedbackText.text = getString(
                R.string.catalog_fragments_with_bracket,
                product.feedback.count.toString())

         if (product.favourite) {
                favoriteButton.setImageResource(com.em.theme.R.drawable.ic_type_heart__state_active)
            } else {
                favoriteButton.setImageResource(com.em.theme.R.drawable.ic_type_heart__state_default)
            }



            }
        }


    class ProductViewHolder(
        val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root)


}