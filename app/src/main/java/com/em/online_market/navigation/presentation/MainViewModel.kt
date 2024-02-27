package com.em.online_market.navigation.presentation

import com.em.online_market.navigation.GlobalNavComponentRouter
import com.em.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter
) : BaseViewModel() {

    private val titleFlow = liveValue<String?>()

    init {
        observeDestinationIdForToolbarInfo()
    }







    private fun merge(
        title: String
    ): ToolbarState{
        return  ToolbarState(
            title = title,
        )
    }

    private fun observeDestinationIdForToolbarInfo(){
     /*   viewModelScope.launch {
            if (destinationId == com.em.authorization.R.layout.fragment_authorization){
                titleFlow.value = Core.resources.getString(com.em.online_market.R.string.authorization_title)
                showToolbarButtonsFlow.value = false
                showToolbarFilterFlow.value = false
            }
        }*/
    }



    class ToolbarState(
        val title: String?,
    )


}