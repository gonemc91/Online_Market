package com.em.catalog.presentation.favourites

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

    private val selectionsFavorite = Selections()


    init {
        viewModelScope.launch {
            val favouritesIds = getFavouritesUseCase.getFavouritesId().unwrapFirst()
            favouritesIds.forEach {
                selectionsFavorite.toggle(it)
            }
        }
    }

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
        selectionsFavorite.flow(),
        filterFLow,
        ::merge
    ).toLiveValue()


    private fun merge(
        productList: Container<List<Product>>,
        selectionsFavorites: SelectionState,
        filter: OnChange<ProductFilter>,
    ): Container<State> {
        return productList.map { listProducts ->
            val productListWithInfo = emptyList<ProductWithInfo>().toMutableList()

            Core.logger.log("Filter in combine favourites  ${filter.value}")

            listProducts.map { product ->
                if (selectionsFavorites.isChecked(product.id)) {
                    productListWithInfo.add(
                        ProductWithInfo(
                            product = product,
                            favourite = selectionsFavorites.isChecked(product.id)
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


    fun launchDetails(productWithCartInfo: ProductWithInfo) = debounce {
        catalogRouter.launchDetails(productWithCartInfo)
    }


    fun toggleFavouriteFlag(product: ProductWithInfo) = viewModelScope.launch {
        selectionsFavorite.toggle(product.product.id)

        if(selectionsFavorite.isChecked(product.product.id)){
            addToFavoritesUseCase.addToFavorites(product.product.id)
        }else{
           deleteFavouritesUseCase.deleteFavouritesItem(product.product.id)
        }

    }


    class State(
        val products: List<ProductWithInfo>,
        val filter: ProductFilter,
    )


}

