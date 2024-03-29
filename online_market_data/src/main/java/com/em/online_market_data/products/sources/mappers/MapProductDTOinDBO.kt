package com.em.online_market_data.products.sources.mappers

import com.em.api.models.FeedbackDTO
import com.em.api.models.InfoProductDTO
import com.em.api.models.PriceDTO
import com.em.api.models.ProductDTO

import com.em.online_market_data.products.entites.product_models.FeedbackDBO
import com.em.online_market_data.products.entites.product_models.InfoProductDBO
import com.em.online_market_data.products.entites.product_models.PriceDBO
import com.em.online_market_data.products.entites.product_models.ProductDBO
import com.em.online_market_data.products.entites.product_models.ProductImagesDBO

fun mapProductDTOInDBO(productDTO: ProductDTO, productImages:  Map<String, ProductImagesDBO> ): ProductDBO{
    return ProductDBO(
        id = productDTO.id,
        title = productDTO.title,
        subtitle = productDTO.subtitle,
        price = mapProductPriceDTOInDBO(productDTO.price),
        feedback = productFeedbackDTOInDBO(productDTO.feedback),
        tags = productDTO.tags,
        available = productDTO.available,
        description = productDTO.description,
        info = productInfoDTOInDBO(productDTO.info),
        ingredients = productDTO.ingredients,
        images = productImages[productDTO.id]
    )
}

private fun mapProductPriceDTOInDBO(productPriceDTO: PriceDTO): PriceDBO {
    return PriceDBO(
        price = productPriceDTO.price.toInt(),
        discount = productPriceDTO.discount,
        unit = productPriceDTO.unit,
        priceWithDiscount = productPriceDTO.priceWithDiscount
    )
}

private fun productFeedbackDTOInDBO(feedbackDTO: FeedbackDTO): FeedbackDBO{
   return FeedbackDBO(
        count = feedbackDTO.count,
        rating = feedbackDTO.rating
    )
}


private fun productInfoDTOInDBO(infoProductDTOList: List<InfoProductDTO>): List<InfoProductDBO>{
    return infoProductDTOList.map { infoProduct->
        InfoProductDBO(
            title = infoProduct.title,
            value = infoProduct.value
        )
    }
}

