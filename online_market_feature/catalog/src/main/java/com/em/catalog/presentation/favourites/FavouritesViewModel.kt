package com.em.catalog.presentation.favourites

import com.em.catalog.CatalogRouter
import com.em.catalog.domain.AddToFavoritesUseCase
import com.em.catalog.domain.DeleteFavouritesUseCase
import com.em.catalog.domain.GetCatalogUseCase
import com.em.catalog.domain.GetFavouritesUseCase
import com.em.catalog.domain.entitys.filter.ProductFilter
import com.em.catalog.domain.entitys.product.Product
import com.em.common.Container
import com.em.common.entities.OnChange
import com.em.common.unwrapFirst
import com.em.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val getCatalogUseCase: GetCatalogUseCase,
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val deleteFavouritesUseCase: DeleteFavouritesUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val catalogRouter: CatalogRouter,
) : BaseViewModel() {

    private val filterFLow = MutableStateFlow(OnChange(ProductFilter.EMPTY))
    private val getChangeFavourites = getFavouritesUseCase.getFavouritesId()



    var filter: ProductFilter
        get() = filterFLow.value.value
        set(value){
            filterFLow.value = OnChange(value)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val productWithFilterFlow = filterFLow
        .flatMapLatest { filter->
            getCatalogUseCase.getProducts(filter.value)
        }

    val stateLiveValue = combine(
        productWithFilterFlow,
        getChangeFavourites,
        filterFLow,
        ::merge
    ).toLiveValue()

    private fun merge(
        productList: Container<List<Product>>,
        selectionsFavorites: Container<Set<String>>,
        filter: OnChange<ProductFilter>,
    ): Container<State> {
        return productList.map { listProducts ->
            val productListWithInfo = emptyList<Product>().toMutableList()
            listProducts.map { product ->
                if ( selectionsFavorites.unwrap().contains(product.id)) {
                    productListWithInfo.add(
                        product.copy(
                            favourite =  selectionsFavorites.unwrap().contains(product.id)
                        )
                    )
                }
            }
            State(
                filter = filter.value,
                products = productListWithInfo,
            )
        }
    }

    fun launchDetails(productWithCartInfo: Product) = debounce {
        catalogRouter.launchDetails(productWithCartInfo)
    }


    fun toggleFavouriteFlag(product: Product) = viewModelScope.launch {
        val hasFavouritesId = getChangeFavourites.unwrapFirst().contains(product.id)
        if(!hasFavouritesId){
            addToFavoritesUseCase.addToFavorites(product.id)
        }else{
            deleteFavouritesUseCase.deleteFavouritesItem(product.id)
        }
    }


    class State(
        val products: List<Product>,
        val filter: ProductFilter,
    )


}

