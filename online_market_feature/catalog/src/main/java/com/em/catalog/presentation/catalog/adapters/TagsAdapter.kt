package com.em.catalog.presentation.catalog.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elveum.elementadapter.resources
import com.em.catalog.R
import com.em.catalog.databinding.ItemTagBinding
import com.em.catalog.domain.entitys.filter.Tag

interface TagsActionListener {
    fun onRootClick (tag: Tag)

}

class TagsAdapter(
    private val actionListener: TagsActionListener
) : RecyclerView.Adapter<TagsAdapter.TagsViewHolder>(), View.OnClickListener {

    var tags: List<Tag> = emptyList()
        set(newValue) {
            val diffCallback = TagsDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onClick(v: View) {
        val tag = v.tag as Tag
        when (v.id) {
            R.id.tagLayout -> {
                actionListener.onRootClick(tag)
            }
        }
    }

    override fun getItemCount(): Int = tags.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTagBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)

        return TagsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagsViewHolder, position: Int) {
        val tag = tags[position]

        with(holder.binding) {
            holder.itemView.tag = tag

            if (tag.active) {
                root.background.setTint(resources().getColor(com.em.theme.R.color.element_dark_blue))
                textViewTag.setTextColor(resources().getColor(com.em.theme.R.color.text_white))
                deleteTag.visibility = View.VISIBLE
            } else {
                root.background.setTint(resources().getColor(com.em.theme.R.color.background_light_grey))
                textViewTag.setTextColor(resources().getColor(com.em.theme.R.color.text_grey))
                deleteTag.visibility = View.GONE
            }
            textViewTag.text = tag.name

        }
    }

    class TagsViewHolder(
        val binding: ItemTagBinding
    ) : RecyclerView.ViewHolder(binding.root)


}


class TagsDiffCallback(
    private val oldList: List<Tag>,
    private val newList: List<Tag>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldTags = oldList[oldItemPosition]
        val newTags = newList[newItemPosition]
        return oldTags.uuidTag == newTags.uuidTag
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldTags = oldList[oldItemPosition]
        val newTags = newList[newItemPosition]
        return oldTags == newTags
    }
}