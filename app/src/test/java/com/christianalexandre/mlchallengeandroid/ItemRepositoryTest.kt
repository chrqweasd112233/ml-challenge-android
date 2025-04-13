package com.christianalexandre.mlchallengeandroid

import com.christianalexandre.mlchallengeandroid.data.api.ItemApiService
import com.christianalexandre.mlchallengeandroid.data.util.SearchResponseDTO
import com.christianalexandre.mlchallengeandroid.data.repository.ItemRepository
import com.christianalexandre.mlchallengeandroid.data.util.ApiException
import com.christianalexandre.mlchallengeandroid.data.util.ApiResponse
import com.christianalexandre.mlchallengeandroid.domain.repository.ItemRepositoryImpl
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


class ItemRepositoryTest {

    private lateinit var itemRepository: ItemRepository
    private val mockItemApiService = mockk<ItemApiService>()

    @Before
    fun setup() {
        itemRepository = ItemRepositoryImpl(mockItemApiService)
    }

    @After
    fun tearDown() {
        clearMocks(mockItemApiService)
    }

    @Test
    fun `test search success`() = runTest {
        val responseMock = ApiResponse.Success(SearchMockManager.searchResponseDTOMock)

        coEvery { mockItemApiService.search("mock") } returns responseMock

        val result = itemRepository.search("mock")

        assertTrue(result is ApiResponse.Success)
        assertNotNull((result as ApiResponse.Success).data)
        assertEquals("title mock", result.data?.firstOrNull()?.title)
        assertEquals(responseMock.data?.results?.firstOrNull()?.id, result.data?.firstOrNull()?.id)
    }

    @Test
    fun `test search error`() = runTest {
        val responseMock = ApiResponse.Error<SearchResponseDTO>(ApiException(500, "Exception mock"))

        coEvery { mockItemApiService.search("mock") } returns responseMock

        val result = itemRepository.search("mock")

        assertTrue(result is ApiResponse.Error)
        assertEquals("Exception mock", result.error?.message)
    }

    @Test
    fun `test search success when data has nullable values`() = runTest {
        val responseMock = ApiResponse.Success(SearchMockManager.searchResponseDTONullableMock)

        coEvery { mockItemApiService.search("mock") } returns responseMock

        val result = itemRepository.search("mock")

        assertTrue(result is ApiResponse.Success)
        assertNotNull((result as ApiResponse.Success).data)
        assertEquals(null, result.data?.firstOrNull()?.siteId)
        assertEquals(responseMock.data?.results?.firstOrNull()?.id, result.data?.firstOrNull()?.id)
    }
}