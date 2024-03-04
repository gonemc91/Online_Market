package com.em.online_market.glue.catalog.mappers

import com.em.catalog.domain.entitys.product.Feedback
import com.em.catalog.domain.entitys.product.InfoProduct
import com.em.catalog.domain.entitys.product.Price
import com.em.catalog.domain.entitys.product.Product
import com.em.catalog.domain.entitys.product.ProductImages
import com.em.online_market_data.products.entites.product_models.FeedbackDBO
import com.em.online_market_data.products.entites.product_models.InfoProductDBO
import com.em.online_market_data.products.entites.product_models.PriceDBO
import com.em.online_market_data.products.entites.product_models.ProductDBO
import com.em.online_market_data.products.entites.product_models.ProductImagesDBO



fun ProductDBO.toProduct(): Product{
    var idSequence: Long = 0
    return Product(
        uuid = ++idSequence,
        id = this.id,
        title = this.title,
        subtitle = this.subtitle,
        price = mapProductPriceFromDBO(this.price),
        feedback = productFeedbackFromDBO(this.feedback),
        tags = this.tags,
        available = this.available,
        description = this.description,
        info = productInfoFromDBO(this.info),
        ingredients = this.ingredients,
        images = this.images?.let { productImagesFromDBO(it) }
    )
}

private fun mapProductPriceFromDBO(productPriceDBO: PriceDBO): Price {
    return Price(
        price = productPriceDBO.price,
        discount = productPriceDBO.discount,
        unit = productPriceDBO.unit,
        priceWithDiscount = productPriceDBO.priceWithDiscount.toString()
    )
}

private fun productFeedbackFromDBO(feedbackDBO: FeedbackDBO): Feedback {
    return Feedback(
        count = feedbackDBO.count,
        rating = feedbackDBO.rating
    )
}

private fun productInfoFromDBO(infoProductDBOList: List<InfoProductDBO>): List<InfoProduct> {
    return infoProductDBOList.map { infoProduct ->
        InfoProduct(
            title = infoProduct.title,
            value = infoProduct.value
        )
    }
}

private fun productImagesFromDBO(productImages: ProductImagesDBO): ProductImages {
    return ProductImages(
        productImages.image1,
        productImages.image2
    )
}
