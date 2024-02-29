package com.em.catalog.presentation.catalog

import com.em.catalog.CatalogRouter
import com.em.catalog.domain.AddToFavoritesUseCase
import com.em.catalog.domain.GetCatalogUseCase
import com.em.catalog.domain.entitys.filter.ProductFilter
import com.em.catalog.domain.entitys.product.ProductWithCartInfo
import com.em.common.Container
import com.em.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getCatalogUseCase: GetCatalogUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val catalogRouter: CatalogRouter,
) : BaseViewModel() {

    private val filterFLow = MutableStateFlow(ProductFilter.EMPTY)

    @OptIn(ExperimentalCoroutinesApi::class)
    val stateLiveValue = filterFLow
        .flatMapLatest { filter->
            getCatalogUseCase.getProducts(filter).map { container->
                container.map { products->
                    State(products, filter)
                }
            }
        }
        .toLiveValue(initialValue = Container.Pending)

    init {
       /* viewModelScope.launch {
            catalogFilterRouter.receiveFilterResults().collectLatest {
                filterFLow.value = it
            }
        }*/
    }

    fun launchFilter() = debounce {
        /*catalogFilterRouter.launchFilter(filterFLow.value)*/
    }

    fun launchDetails(productWithCartInfo: ProductWithCartInfo) = debounce {
        catalogRouter.launchDetails(productWithCartInfo.product.id)
    }

    fun addToCart(productWithCartInfo: ProductWithCartInfo) = debounce {
        viewModelScope.launch {
            //addToFavoritesUseCase.addToFavorites(productWithCartInfo.product)
        }
    }


    fun deleteOnFavorites(product: ProductWithCartInfo) {
        TODO()
    }

    fun addToFavorites(product: ProductWithCartInfo) {
        TODO()
    }


    class State(
        val products: List<ProductWithCartInfo>,
        val filter: ProductFilter,
    )







}
