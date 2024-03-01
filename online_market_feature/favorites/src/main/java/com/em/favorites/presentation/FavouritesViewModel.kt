package com.em.favorites.presentation

import com.em.catalog.domain.entitys.filter.ProductFilter
import com.em.catalog.domain.entitys.product.ProductWithInfo
import com.em.favorites.domain.AddToFavoritesUseCase
import com.em.favorites.domain.DeleteFavouritesUseCase
import com.em.favorites.domain.GetFavouritesProductsUseCase
import com.em.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val getFavouritesProductsUseCase: GetFavouritesProductsUseCase,
    private val deleteFavouritesUseCase: DeleteFavouritesUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
) : BaseViewModel() {

    private val filterFLow = MutableStateFlow(ProductFilter.EMPTY)

    @OptIn(ExperimentalCoroutinesApi::class)
    val favouritesProductWithFilterFlow = filterFLow
        .flatMapLatest { filter->
            getFavouritesProductsUseCase.getFavouritesProducts(filter)
        }
/*

    val stateLiveValue = combine(
        favouritesProductWithFilterFlow,
        filterFLow,
        ::merge
    ).toLiveValue()

    private fun merge(
        productList: Container<List<Product>>,
        filter: ProductFilter,
    ): Container<State> {
        return productList.map { listProducts ->
            val productListWithInfo = emptyList<ProductWithInfo>().toMutableList()
            listProducts.map { product ->

                val favoriteId = favouritesIds.contains(product.id)
                if (favoriteId) {
                    productListWithInfo.add(
                        ProductWithInfo(product, true)
                    )
                } else {
                    productListWithInfo.add(
                        ProductWithInfo(product, false)
                    )
                }
            }
            productListWithInfo.forEach {
                Core.logger.log("$it")
            }
            State(
                filter = filter,
                products = productListWithInfo
            )
        }
    }
*/


    fun deleteOnFavorites(productId: String) {
        viewModelScope.launch {
            deleteFavouritesUseCase.deleteFavouritesItem(productId)
        }
    }

    fun addToFavorites(productId: String) {
        viewModelScope.launch {
            addToFavoritesUseCase.addToFavorites(productId)
        }
    }

    class State(
        val filter: ProductFilter,
        val products: List<ProductWithInfo>,
    )
}
