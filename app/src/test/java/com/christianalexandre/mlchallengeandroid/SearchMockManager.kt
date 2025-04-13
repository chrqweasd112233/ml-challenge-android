package com.christianalexandre.mlchallengeandroid

import com.christianalexandre.mlchallengeandroid.data.model.AttributesDTO
import com.christianalexandre.mlchallengeandroid.data.model.SearchApiPaginationDTO
import com.christianalexandre.mlchallengeandroid.data.model.SearchItemDTO
import com.christianalexandre.mlchallengeandroid.data.model.SearchResponseDTO
import com.christianalexandre.mlchallengeandroid.data.model.SellerDTO
import com.christianalexandre.mlchallengeandroid.data.model.ShippingDTO
import com.christianalexandre.mlchallengeandroid.data.model.toDomain

object SearchMockManager {
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
}