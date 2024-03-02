package com.em.catalog.presentation.catalog

import com.em.catalog.CatalogRouter
import com.em.catalog.domain.AddToFavoritesUseCase
import com.em.catalog.domain.DeleteFavouritesUseCase
import com.em.catalog.domain.GetCatalogUseCase
import com.em.catalog.domain.GetFavouritesUseCase
import com.em.catalog.domain.entitys.filter.ProductFilter
import com.em.catalog.domain.entitys.product.Product
import com.em.catalog.domain.entitys.product.ProductWithInfo
import com.em.common.Container
import com.em.common.Core
import com.em.common.entities.OnChange
import com.em.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getCatalogUseCase: GetCatalogUseCase,
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val deleteFavouritesUseCase: DeleteFavouritesUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val catalogRouter: CatalogRouter,
) : BaseViewModel() {

    private val filterFLow = MutableStateFlow(OnChange(ProductFilter.EMPTY))



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
        getFavouritesUseCase.getFavouritesId(),
        filterFLow,
        ::merge
    ).toLiveValue()



    private fun merge(
        productList: Container<List<Product>>,
        getFavouritesIDs: Container<Set<String>>,
        filter: OnChange<ProductFilter>,
    ): Container<State> {
        return productList.map { listProducts ->
            Core.logger.log(" Filter ${filter.value}")
            val productListWithInfo = emptyList<ProductWithInfo>().toMutableList()
            val tagsList  = emptyList<String>().toMutableList()
            listProducts.map { product ->
                product.tags.forEach{
                    tagsList.add(it)
                }

                val favouritesIds = getFavouritesIDs.unwrap()
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
            State(
                filter = filter.value,
                products = productListWithInfo,
                listTag = tagsList.toSet()
            )
        }
    }


    fun launchDetails(productWithCartInfo: ProductWithInfo) = debounce {
        catalogRouter.launchDetails(productWithCartInfo)
    }



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
        val products: List<ProductWithInfo>,
        val filter: ProductFilter,
        val listTag: Set<String>,
    )
}
