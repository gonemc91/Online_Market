package com.em.catalog.presentation.catalog

import com.em.catalog.CatalogRouter
import com.em.catalog.domain.AddToFavoritesUseCase
import com.em.catalog.domain.DeleteFavouritesUseCase
import com.em.catalog.domain.GetCatalogUseCase
import com.em.catalog.domain.GetFavouritesUseCase
import com.em.catalog.domain.GetTagsValueUseCase
import com.em.catalog.domain.entitys.filter.ProductFilter
import com.em.catalog.domain.entitys.filter.Tag
import com.em.catalog.domain.entitys.product.Product
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
    getTagsValueUseCase: GetTagsValueUseCase,
    private val catalogRouter: CatalogRouter,
) : BaseViewModel() {


    private val filterFLow = MutableStateFlow(OnChange(ProductFilter.EMPTY))

    private val selectionsFavorite = Selections()
    private val selectionsFilterTags = MutableStateFlow(AllTAGS)


    init {
        Core.logger.log("Start Init catalog")
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
        getTagsValueUseCase.getAllTags(),
        selectionsFavorite.flow(),
        selectionsFilterTags,
        filterFLow,
        ::merge
    ).toLiveValue()


    private fun merge(
        productList: Container<List<Product>>,
        tagList: Container<Set<String>>,
        selectionsFavorites: SelectionState,
        selectionsTags: String,
        filter: OnChange<ProductFilter>,
    ): Container<State> {
        return productList.map { listProducts ->
            val productListWithInfo = emptyList<Product>().toMutableList()
            val tagsList  = emptyList<String>().toMutableList()
            tagsList.add(AllTAGS)
            Core.logger.log("Filter in combine  ${filter.value}")


            val tags = emptyList<Tag>().toMutableSet()
            //////////////////////////////////////
            listProducts.map { product ->
                tagList.unwrap().forEach {
                    tagsList.add(it)
                }
                tagsList.map {
                    var idSequence: Long = 0
                    tags.add(
                        Tag(
                            uuidTag = ++idSequence,
                            name = it,
                            active = it == selectionsTags
                        )
                    )
                }
                //////////////////////////////////

                productListWithInfo.add(
                    product.copy(
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

    fun launchDetails(productWithCartInfo: Product) = debounce {
        catalogRouter.launchDetails(productWithCartInfo)
    }


    fun toggleFavouriteFlag(product: Product) = viewModelScope.launch {
        selectionsFavorite.toggle(product.id)

        if(selectionsFavorite.isChecked(product.id)){
            addToFavoritesUseCase.addToFavorites(product.id)
        }else{
           deleteFavouritesUseCase.deleteFavouritesItem(product.id)
        }

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
        val products: List<Product>,
        val filter: ProductFilter,
        val listTag: List<Tag>,
    )

    companion object {
        const val AllTAGS = "Смотреть все"
    }
}

