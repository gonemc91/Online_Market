package com.em.profile

interface ProfileRouter {


    /**
     * Launch the screen with favourites product.
     */
    fun launchFavouriteProductScreen()



    /**
     * Go back to the previous screen.
     */
    fun goBack()


    /**
     * Close all screen and launch the initial screen
     */
    fun restartApp()

}