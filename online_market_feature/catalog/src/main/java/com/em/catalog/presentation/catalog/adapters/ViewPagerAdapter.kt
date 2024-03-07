package com.em.catalog.presentation.catalog.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.em.catalog.databinding.ItemPBinding
import com.em.presentation.loadResources

class ViewPagerAdapter : RecyclerView.Adapter<PagerVH>() {

    var positionOut = 0
    var productImages: List<Int> = emptyList()
        set(newValue) {
            field = newValue
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPBinding.inflate(inflater, parent, false)
        return PagerVH(binding)
    }


    override fun getItemCount(): Int = productImages.size

    override fun onBindViewHolder(holder: PagerVH, position: Int) {
        with(holder.binding){
            positionOut = position
            val imageProduct = productImages[position]

            imageProduct.let {image.loadResources(it)}
        }
    }
}




class PagerVH(val binding: ItemPBinding) : RecyclerView.ViewHolder(binding.root)



