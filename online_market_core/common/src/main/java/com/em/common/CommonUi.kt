package com.em.common


/**
 * General ui actions that can be executed anywhere
 */

interface CommonUi {

    /**
     * Show a short message to the user.
     */

    fun toast(message: String)

    /**
     * Show a simple alert dialog with title, message, positive action and #
     * optional negative action.
     * @return the user choice (true - positive action, false - negative action)
     */


    suspend fun alertDialog(config: AlertDialogConfig): Boolean

}