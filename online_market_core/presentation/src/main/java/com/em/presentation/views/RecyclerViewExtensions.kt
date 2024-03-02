package com.em.presentation.views

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setupGridLayout(){
    layoutManager = GridLayoutManager(context, 2)
   (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
}


fun RecyclerView.simpleList(){
    layoutManager = LinearLayoutManager(context)
    (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
}


fun RecyclerView.simpleListHorizontal(){
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
}