package com.christianalexandre.mlchallengeandroid

import com.christianalexandre.mlchallengeandroid.data.api.SearchApiService
import com.christianalexandre.mlchallengeandroid.data.model.AttributesDTO
import com.christianalexandre.mlchallengeandroid.data.model.SearchApiPaginationDTO
import com.christianalexandre.mlchallengeandroid.data.model.SearchItemDTO
import com.christianalexandre.mlchallengeandroid.data.model.SearchResponseDTO
import com.christianalexandre.mlchallengeandroid.data.model.SellerDTO
import com.christianalexandre.mlchallengeandroid.data.model.ShippingDTO
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
        val responseMock = ApiResponse.Success(SearchMockManager.searchResponseDTOMock)

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
        val responseMock = ApiResponse.Success(SearchMockManager.searchResponseDTONullableMock)

        coEvery { mockSearchApiService.search("mock") } returns responseMock

        val result = searchRepository.search("mock")

        assertTrue(result is ApiResponse.Success)
        assertNotNull((result as ApiResponse.Success).data)
        assertEquals(null, result.data?.firstOrNull()?.siteId)
        assertEquals(responseMock.data?.results?.firstOrNull()?.id, result.data?.firstOrNull()?.id)
    }
}