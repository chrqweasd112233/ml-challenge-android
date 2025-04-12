package com.christianalexandre.mlchallengeandroid

import com.christianalexandre.mlchallengeandroid.data.api.SearchApiService
import com.christianalexandre.mlchallengeandroid.data.model.AttributesDTO
import com.christianalexandre.mlchallengeandroid.data.model.SearchApiPaginationDTO
import com.christianalexandre.mlchallengeandroid.data.model.SearchItemDTO
import com.christianalexandre.mlchallengeandroid.data.model.SearchResponseDTO
import com.christianalexandre.mlchallengeandroid.data.model.SellerDTO
import com.christianalexandre.mlchallengeandroid.data.repository.SearchRepository
import com.christianalexandre.mlchallengeandroid.data.util.ApiException
import com.christianalexandre.mlchallengeandroid.data.util.ApiResponse
import com.christianalexandre.mlchallengeandroid.domain.repository.SearchRepositoryImpl
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test


class SearchRepositoryTest {

    private lateinit var searchRepository: SearchRepository
    private val mockSearchApiService = mockk<SearchApiService>()

    @Before
    fun setup() {
        searchRepository = SearchRepositoryImpl(mockSearchApiService)
    }

    @After
    fun tearDown() {
        clearMocks(mockSearchApiService)
    }

    @Test
    fun `test search success`() = runTest {
        val responseMock = mockSearchApiServiceSuccess()

        coEvery { mockSearchApiService.search("mock") } returns responseMock

        val result = searchRepository.search("mock")

        assertTrue(result is ApiResponse.Success)
        assertNotNull((result as ApiResponse.Success).data)
        assertEquals("title mock", result.data?.firstOrNull()?.title)
        assertEquals(responseMock.data?.results?.firstOrNull()?.id, result.data?.firstOrNull()?.id)
    }

    @Test
    fun `test search error`() = runTest {
        val responseMock = ApiResponse.Error<SearchResponseDTO>(ApiException(500, "Exception mock"))

        coEvery { mockSearchApiService.search("mock") } returns responseMock

        val result = searchRepository.search("mock")

        assertTrue(result is ApiResponse.Error)
        assertEquals("Exception mock", result.error?.message)
    }

    @Test
    fun `test search success when data has nullable values`() = runTest {
        val responseMock = mockSearchApiServiceNullable()

        coEvery { mockSearchApiService.search("mock") } returns responseMock

        val result = searchRepository.search("mock")

        assertTrue(result is ApiResponse.Success)
        assertNotNull((result as ApiResponse.Success).data)
        assertEquals(null, result.data?.firstOrNull()?.siteId)
        assertEquals(responseMock.data?.results?.firstOrNull()?.id, result.data?.firstOrNull()?.id)
    }

    private fun mockSearchApiServiceSuccess() = ApiResponse.Success(
        SearchResponseDTO(
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
    )

    private fun mockSearchApiServiceNullable() = ApiResponse.Success(
        SearchResponseDTO(
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
                    seller = null,
                    attributes = null
                )
            )
        )
    )
}