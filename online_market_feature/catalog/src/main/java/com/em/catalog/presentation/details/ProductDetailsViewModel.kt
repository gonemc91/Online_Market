package com.em.catalog.presentation.details


import com.em.catalog.domain.AddToFavoritesUseCase
import com.em.catalog.domain.DeleteFavouritesUseCase
import com.em.catalog.domain.GetProductsDetailsUseCase
import com.em.catalog.domain.entitys.product.Product
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
    private val deleteFavouritesUseCase: DeleteFavouritesUseCase,
) : BaseViewModel(){

    private val addFavoritesInFavoriteFlow = MutableStateFlow(screen.productId.favourite)

    private val productFlow = MutableStateFlow(Container.Success(screen.productId))



    val stateLiveValue = combine(
        productFlow,
        addFavoritesInFavoriteFlow,
        ::merge)
        .toLiveValue(Container.Pending)

    fun reload() = debounce {
        getProductsDetailsUseCase.reload()
    }

    fun  addToFavorites(product: Product) = viewModelScope.launch {
        val isChecked = addFavoritesInFavoriteFlow.value
        if(!isChecked){
            addFavoritesInFavoriteFlow.value = true
            addToFavoritesUseCase.addToFavorites(product.id)
        }else{
            addFavoritesInFavoriteFlow.value = false
            deleteFavouritesUseCase.deleteFavouritesItem(product.id)
        }

    }

    private fun merge(
        productContainer: Container<Product>,
        isAddToFavorite: Boolean
    ): Container<State>{
        return productContainer.map{product ->
            State(
                product= product,
                addToFavorite = isAddToFavorite
            )
        }
    }

    @AssistedFactory
    interface Factory{
        fun create(screen: ProductDetailsFragment.Screen): ProductDetailsViewModel
    }

    class State(
        product: Product,
        private val addToFavorite: Boolean,

    ){

        val productUI = product
        val favoritesButtonState: Boolean get() = !addToFavorite


    }
}