package com.em.presentation

import android.widget.ImageView
import androidx.annotation.DrawableRes
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation

/**
 * Load the image URL int [ImageView] and round image corners.
 */

fun ImageView.loadUrl(url: String, mode: Mode){
    load(url){
        when(mode){
            Mode.CIRCLE_CROP ->transformations(CircleCropTransformation())
            Mode.RECTANGLE_CORNER_16F ->transformations(RoundedCornersTransformation(16f))
        }
    }
}


/**
 * Load the image resource into [ImageView] and round image corners.
 */

fun ImageView.loadResources(@DrawableRes id: Int){
    load(id){
    }
}


enum class Mode {
    CIRCLE_CROP,
    RECTANGLE_CORNER_16F
}
