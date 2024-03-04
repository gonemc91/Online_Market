package com.em.presentation.views

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setupGridLayout(){
    layoutManager = GridLayoutManager(context, 2)
   (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
}


fun RecyclerView.simpleList(){
    addItemDecoration(OffSetItemDecoration(bottomOffset = 50))
    layoutManager = LinearLayoutManager(context)
    (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
}


fun RecyclerView.simpleListHorizontal(){
    addItemDecoration(OffSetItemDecoration(rightOffset = 24))
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
}


class OffSetItemDecoration(
    private val leftOffset: Int = 0,
    private val rightOffset: Int = 0,
    private val topOffset: Int = 0,
    private val bottomOffset: Int = 0,
): RecyclerView.ItemDecoration(){
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(leftOffset,topOffset, rightOffset, bottomOffset)
    }
}