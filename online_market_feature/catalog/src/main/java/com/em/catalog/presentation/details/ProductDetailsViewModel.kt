package com.em.catalog.presentation.details


import com.em.catalog.domain.AddToFavoritesUseCase
import com.em.catalog.domain.GetProductsDetailsUseCase
import com.em.catalog.domain.entitys.product.ProductWithInfo
import com.em.common.Container
import com.em.presentation.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch


class ProductDetailsViewModel @AssistedInject constructor(
    @Assisted private val screen: ProductDetailsFragment.Screen,
    private val getProductsDetailsUseCase: GetProductsDetailsUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
) : BaseViewModel(){

    private val addFavoritesInFavoriteFlow = MutableStateFlow(false)
    private val productFlow = MutableStateFlow(Container.Success(screen.productId))

    val stateLiveValue = combine(
        productFlow,
        addFavoritesInFavoriteFlow,
        ::merge)
        .toLiveValue(Container.Pending)

    fun reload() = debounce {
        getProductsDetailsUseCase.reload()
    }

    fun addToFavorites() = debounce {
        viewModelScope.launch {
            val state = stateLiveValue.value.getOrNull() ?: return@launch
            try {
                addFavoritesInFavoriteFlow.value = true
                //addToFavoritesUseCase.addToFavorite(state.product)
            }finally {
                addFavoritesInFavoriteFlow.value = false
            }
        }
    }

    private fun merge(
        productContainer: Container<ProductWithInfo>,
        isAddToFavorite: Boolean
    ): Container<State>{
        return productContainer.map{productWithCartInfo ->
            State(
                productWithCartInfo = productWithCartInfo,
                addToFavorite = isAddToFavorite
            )
        }
    }

    @AssistedFactory
    interface Factory{
        fun create(screen: ProductDetailsFragment.Screen): ProductDetailsViewModel
    }

    class State(
        private val productWithCartInfo: ProductWithInfo,
        private val addToFavorite: Boolean,
    ){
        val product = productWithCartInfo.product
        val favoritesButtonState: Boolean get() = !addToFavorite
        
    }
}