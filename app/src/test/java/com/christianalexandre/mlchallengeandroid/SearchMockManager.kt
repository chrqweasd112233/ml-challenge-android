package com.christianalexandre.mlchallengeandroid

import com.christianalexandre.mlchallengeandroid.data.model.itemdetail.ItemDescriptionDTO
import com.christianalexandre.mlchallengeandroid.data.model.itemdetail.ItemDetailDTO
import com.christianalexandre.mlchallengeandroid.data.model.itemdetail.PictureDTO
import com.christianalexandre.mlchallengeandroid.data.model.itemdetail.toDomain
import com.christianalexandre.mlchallengeandroid.data.model.search.AttributesDTO
import com.christianalexandre.mlchallengeandroid.data.util.SearchApiPaginationDTO
import com.christianalexandre.mlchallengeandroid.data.model.search.SearchItemDTO
import com.christianalexandre.mlchallengeandroid.data.util.SearchResponseDTO
import com.christianalexandre.mlchallengeandroid.data.model.search.SellerDTO
import com.christianalexandre.mlchallengeandroid.data.model.search.ShippingDTO
import com.christianalexandre.mlchallengeandroid.data.model.search.toDomain
import com.christianalexandre.mlchallengeandroid.domain.model.ItemDetail

object SearchMockManager {
    // region Search
    val searchResponseDTOMock = SearchResponseDTO(
        siteId = "site id mock",
        query = "query mock",
        paging = SearchApiPaginationDTO(
            total = 0,
            off = 0,
            limit = 0,
            primaryResults = 0
        ),
        results = listOf(
            SearchItemDTO(
                id = "id mock",
                siteId = "site id mock",
                categoryId = "category mock",
                title = "title mock",
                price = 0.0,
                originalPrice = 0.0,
                thumbnail = "thumbnail mock",
                officialStoreName = "store name mock",
                shipping = ShippingDTO(true),
                seller = SellerDTO(
                    id = 0,
                    nickname = "nickname mock"
                ),
                attributes = listOf(
                    AttributesDTO(
                        key = "key mock",
                        value = "value mock"
                    )
                )
            )
        )
    )

    val searchItemMock = searchResponseDTOMock.results?.map { it.toDomain() }

    val searchResponseDTONullableMock = SearchResponseDTO(
        siteId = null,
        query = "query mock",
        paging = null,
        results = listOf(
            SearchItemDTO(
                id = "id mock",
                siteId = null,
                categoryId = "category mock",
                title = "title mock",
                price = 0.0,
                originalPrice = 0.0,
                thumbnail = "thumbnail mock",
                officialStoreName = null,
                shipping = null,
                seller = null,
                attributes = null
            )
        )
    )

    val searchItemNullableMock = searchResponseDTONullableMock.results?.map { it.toDomain() }
    // endregion

    // region Item Detail
    val itemDetailDTOMock = ItemDetailDTO(
        id = "id mock",
        siteId = "site id mock",
        title = "title mock",
        price = 0.0,
        originalPrice = 0.0,
        thumbnail = "thumbnail mock",
        officialStoreName = "name store mock",
        shipping = ShippingDTO(
            freeShipping = true
        ),
        attributes = listOf(
            AttributesDTO(
                key = "key mock",
                value = "value mock"
            )
        ),
        pictures = listOf(
            PictureDTO(
                id = "mock id",
                url = "url id",
                secureUrl = "secure url id"
            )
        ),
        saleTerms = listOf(
            AttributesDTO(
                key = "key mock",
                value = "value mock"
            )
        )
    )

    val itemDetailMock: ItemDetail? = itemDetailDTOMock.toDomain()

    val itemDetailDTONullableMock = ItemDetailDTO(
        id = "id mock",
        siteId = "site id mock",
        title = "title mock",
        price = 0.0,
        originalPrice = 0.0,
        thumbnail = "thumbnail mock",
        officialStoreName = null,
        shipping = null,
        attributes = listOf(
            AttributesDTO(
                key = "key mock",
                value = "value mock"
            )
        ),
        pictures = null,
        saleTerms = null
    )

    val itemDetailNullableMock: ItemDetail? = itemDetailDTONullableMock.toDomain()
    // endregion

    // region Item Description
    val itemDescriptionDTOMock = ItemDescriptionDTO(
        plainText = "Description mock"
    )

    val itemDescriptionMock = itemDescriptionDTOMock.plainText

    val itemDescriptionDTONullableMock = ItemDescriptionDTO(
        plainText = null
    )

    val itemDescriptionNullableMock = itemDescriptionDTONullableMock.plainText
    // endregion
}