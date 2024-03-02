package com.em.catalog.presentation.catalog

import com.em.catalog.CatalogRouter
import com.em.catalog.domain.AddToFavoritesUseCase
import com.em.catalog.domain.DeleteFavouritesUseCase
import com.em.catalog.domain.GetCatalogUseCase
import com.em.catalog.domain.GetFavouritesUseCase
import com.em.catalog.domain.entitys.filter.ProductFilter
import com.em.catalog.domain.entitys.filter.Tag
import com.em.catalog.domain.entitys.product.Product
import com.em.catalog.domain.entitys.product.ProductWithInfo
import com.em.catalog.domain.repositories.ProductsRepository
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
class CatalogViewModel @Inject constructor(
    private val getCatalogUseCase: GetCatalogUseCase,
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val deleteFavouritesUseCase: DeleteFavouritesUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val catalogRouter: CatalogRouter,
    private val productsRepository: ProductsRepository,
) : BaseViewModel() {

    private val tags = mutableSetOf<String>()
    private val tagsFlow = MutableStateFlow(OnChange(tags))

    private val filterFLow = MutableStateFlow(OnChange(ProductFilter.EMPTY))


    private val selectionsFavorite = Selections()
    private val selectionsFilterTags = MutableStateFlow(AllTAGS)


    init {
        viewModelScope.launch {
            val favouritesIds = getFavouritesUseCase.getFavouritesId().unwrapFirst()
            favouritesIds.forEach {
                selectionsFavorite.toggle(it)
            }
            productsRepository.getAllTags().forEach{
                tags.add(it)
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
        selectionsFilterTags,
        filterFLow,
        ::merge
    ).toLiveValue()


    private fun merge(
        productList: Container<List<Product>>,
        selectionsFavorites: SelectionState,
        selectionsTags: String,
        filter: OnChange<ProductFilter>,
    ): Container<State> {
        return productList.map { listProducts ->
            val productListWithInfo = emptyList<ProductWithInfo>().toMutableList()
            val tagsList  = emptyList<String>().toMutableList()
            tagsList.add(AllTAGS)
            Core.logger.log("${filter.value}")

            val tags = emptyList<Tag>().toMutableSet()

            listProducts.map { product ->
                product.tags.forEach {
                    tagsList.add(it)
                }
                tagsList.map {

                    tags.add(
                        Tag(
                            tags = it,
                            active = it == selectionsTags
                        )
                    )
                }

                productListWithInfo.add(
                    ProductWithInfo(
                        product = product,
                        favourite = selectionsFavorites.isChecked(product.id)
                    )
                )
            }
            State(
                filter = filter.value,
                products = productListWithInfo,
                listTag = tags.toList()
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

    fun toggleFavouriteFlag(product: ProductWithInfo) = viewModelScope.launch {
        selectionsFavorite.toggle(product.product.id)
            addToFavoritesUseCase.addToFavorites(product.product.id)
    }

    fun toggleSelectedTAG(it: String) {
        filter = if (it == AllTAGS){
            ProductFilter(tag = null)
        }else{
            ProductFilter(tag = it)
        }

        selectionsFilterTags.value = it
    }



    class State(
        val products: List<ProductWithInfo>,
        val filter: ProductFilter,
        val listTag: List<Tag>,
        )

    companion object {
        const val AllTAGS = "Смотреть все"
    }
}

