package com.christianalexandre.mlchallengeandroid

import com.christianalexandre.mlchallengeandroid.data.api.ItemApiService
import com.christianalexandre.mlchallengeandroid.data.model.itemcategory.ItemCategoryDTO
import com.christianalexandre.mlchallengeandroid.data.model.itemdescription.ItemDescriptionDTO
import com.christianalexandre.mlchallengeandroid.data.model.itemdetail.ItemDetailDTO
import com.christianalexandre.mlchallengeandroid.data.model.itemdetail.toDomain
import com.christianalexandre.mlchallengeandroid.data.model.util.SearchResponseDTO
import com.christianalexandre.domain.model.ApiException
import com.christianalexandre.domain.model.ApiResponse
import com.christianalexandre.mlchallengeandroid.data.repository.ItemRepositoryImpl
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

    private lateinit var itemRepository: com.christianalexandre.domain.repository.ItemRepository
    private val mockItemApiService = mockk<ItemApiService>()

    @Before
    fun setup() {
        itemRepository = ItemRepositoryImpl(mockItemApiService)
    }

    @After
    fun tearDown() {
        clearMocks(mockItemApiService)
    }

    // region Search
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
    // endregion

    // region Item Detail
    @Test
    fun `test get item detail success`() = runTest {
        val responseMock = ApiResponse.Success(SearchMockManager.itemDetailDTOMock)

        coEvery { mockItemApiService.getItemDetail("mock") } returns responseMock

        val result = itemRepository.getItemDetail("mock")

        assertTrue(result is ApiResponse.Success)
        assertNotNull((result as ApiResponse.Success).data)
        assertEquals("title mock", result.data?.title)
        assertEquals(responseMock.data?.id, result.data?.id)
    }

    @Test
    fun `test get item detail error`() = runTest {
        val responseMock = ApiResponse.Error<ItemDetailDTO>(ApiException(500, "Exception mock"))

        coEvery { mockItemApiService.getItemDetail("mock") } returns responseMock

        val result = itemRepository.getItemDetail("mock")

        assertTrue(result is ApiResponse.Error)
        assertEquals("Exception mock", result.error?.message)
    }

    @Test
    fun `test get item detail success when data has nullable values`() = runTest {
        val responseMock = ApiResponse.Success(SearchMockManager.itemDetailDTONullableMock)

        coEvery { mockItemApiService.getItemDetail("mock") } returns responseMock

        val result = itemRepository.getItemDetail("mock")

        assertTrue(result is ApiResponse.Success)
        assertNotNull((result as ApiResponse.Success).data)
        assertEquals(null, result.data?.officialStoreName)
        assertEquals(responseMock.data?.toDomain()?.attributes, result.data?.attributes)
    }
    // endregion

    // region Item Description
    @Test
    fun `test get item description success`() = runTest {
        val responseMock = ApiResponse.Success(SearchMockManager.itemDescriptionDTOMock)

        coEvery { mockItemApiService.getItemDescription("mock") } returns responseMock

        val result = itemRepository.getItemDescription("mock")

        assertTrue(result is ApiResponse.Success)
        assertNotNull((result as ApiResponse.Success).data)
        assertEquals("Description mock", result.data)
    }

    @Test
    fun `test get item description error`() = runTest {
        val responseMock = ApiResponse.Error<ItemDescriptionDTO>(ApiException(500, "Exception mock"))

        coEvery { mockItemApiService.getItemDescription("mock") } returns responseMock

        val result = itemRepository.getItemDescription("mock")

        assertTrue(result is ApiResponse.Error)
        assertEquals("Exception mock", result.error?.message)
    }

    @Test
    fun `test get item description success when data has nullable values`() = runTest {
        val responseMock = ApiResponse.Success(SearchMockManager.itemDescriptionDTONullableMock)

        coEvery { mockItemApiService.getItemDescription("mock") } returns responseMock

        val result = itemRepository.getItemDescription("mock")

        assertTrue(result is ApiResponse.Success)
        assertEquals(null, result.data)
    }
    // endregion

    // region Item Category
    @Test
    fun `test get item category success`() = runTest {
        val responseMock = ApiResponse.Success(SearchMockManager.itemCategoryDTOMock)

        coEvery { mockItemApiService.getItemCategory("mock") } returns responseMock

        val result = itemRepository.getItemCategory("mock")

        assertTrue(result is ApiResponse.Success)
        assertNotNull((result as ApiResponse.Success).data)
    }

    @Test
    fun `test get item category error`() = runTest {
        val responseMock = ApiResponse.Error<ItemCategoryDTO>(ApiException(500, "Exception mock"))

        coEvery { mockItemApiService.getItemCategory("mock") } returns responseMock

        val result = itemRepository.getItemCategory("mock")

        assertTrue(result is ApiResponse.Error)
        assertEquals("Exception mock", result.error?.message)
    }

    @Test
    fun `test get item category success when data has nullable values`() = runTest {
        val responseMock = ApiResponse.Success(SearchMockManager.itemCategoryDTOMockNullableMock)

        coEvery { mockItemApiService.getItemCategory("mock") } returns responseMock

        val result = itemRepository.getItemCategory("mock")

        assertTrue(result is ApiResponse.Success)
        assertEquals(null, result.data?.pathFromRoot)
    }
    // endregion
}