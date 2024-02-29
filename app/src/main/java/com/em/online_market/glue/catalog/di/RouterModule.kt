package com.em.online_market.glue.catalog.di

import com.em.catalog.CatalogRouter
import com.em.online_market.glue.catalog.AdapterCatalogRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface RouterModule {


    @Binds
    fun bindCatalogRouter(
        catalogRouter: AdapterCatalogRouter
    ): CatalogRouter




}