package com.em.online_market_data.products.sources

import com.em.online_market_data.R.drawable
import com.em.online_market_data.products.entites.product_models.ProductImagesDBO
import javax.inject.Inject


class InMemoryProductDataSource @Inject constructor (
): ProductsDataSource {


    private val availableImages = mapOf<String, ProductImagesDBO>(
        Pair("cbf0c984-7c6c-4ada-82da-e29dc698bb50",
            ProductImagesDBO(
            image1 = drawable.product_image_4,
            image2 = drawable.product_image_1)

        ),
        Pair(
            "54a876a5-2205-48ba-9498-cfecff4baa6e",
        ProductImagesDBO(
            image1 = drawable.product_image_2,
            image2 = drawable.product_image_3)
        ),
        Pair(
            "75c84407-52e1-4cce-a73a-ff2d3ac031b3",
        ProductImagesDBO(
            image1 = drawable.product_image_1,
            image2 = drawable.product_image_4)
        ),
        Pair(
            "16f88865-ae74-4b7c-9d85-b68334bb97db",
        ProductImagesDBO(
            image1 = drawable.product_image_6,
            image2 = drawable.product_image_5)
        ),
        Pair(
            "26f88856-ae74-4b7c-9d85-b68334bb97db",
        ProductImagesDBO(
            image1 = drawable.product_image_3,
            image2 = drawable.product_image_6)
        ),
        Pair(
            "15f88865-ae74-4b7c-9d81-b78334bb97db",
        ProductImagesDBO(
            image1 = drawable.product_image_4,
            image2 = drawable.product_image_2)
        ),
        Pair(
            "88f88865-ae74-4b7c-9d81-b78334bb97db",
        ProductImagesDBO(
            image1 = drawable.product_image_5,
            image2 = drawable.product_image_6)
        ),
        Pair(
            "55f58865-ae74-4b7c-9d81-b78334bb97db",
        ProductImagesDBO(
            image1 = drawable.product_image_2,
            image2 = drawable.product_image_1)
        ),
    )

    override suspend fun getImageProducts(): Map<String, ProductImagesDBO> {
        return availableImages
    }


}